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
import com.example.presentation.base.BaseMVVMActivity
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.screens.main.rv_adapter.OnArticleClickListener
import com.example.presentation.screens.show_article.ShowArticle
import kotlinx.coroutines.*
import org.xmlpull.v1.XmlPullParser
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val TEXT_KEY = "key"

class MainActivity : BaseMVVMActivity<ActivityMainBinding, MainViewModel, MainRouter>(), OnArticleClickListener {

    override fun provideLayoutId() = R.layout.activity_main

    override fun provideViewModel() = ViewModelProviders.of(this).get(MainViewModel::class.java)

    override fun provideRouter() = MainRouter(this)

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        observeLiveData()
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