package com.example.presentation.screens.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Xml
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.model.entity.Article
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.screens.main.rv_adapter.OnArticleClickListener
import com.example.presentation.screens.show_article.ShowArticle
import kotlinx.coroutines.*
import org.xmlpull.v1.XmlPullParser
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val TEXT_KEY = "key"

class MainActivity : AppCompatActivity(), OnArticleClickListener {


    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        recyclerView = binding.rvArticles
        recyclerView.adapter = viewModel.adaptRV
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.adaptRV.setListener(this)
        viewModel.onCreate()
        observeLiveData()
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
    }


    private fun observeLiveData() {
        viewModel.listArticleLiveData.observe(this, {
            viewModel.setDataRV(it)
        })
    }

    override fun onClick(article: Article, position: Int) {
        lifecycleScope.launch {
            withContext(Dispatchers.Default) {
                viewModel.addArticleBD(article.apply {
                    isClicked = true
                })
            }
        }
        startActivity(Intent(this, ShowArticle::class.java).putExtra(TEXT_KEY, article.text))
    }

}





var formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
fun log(text: String) {
    Log.d("TAG", "${formatter.format(Date())} $text [${Thread.currentThread().name}]")
}