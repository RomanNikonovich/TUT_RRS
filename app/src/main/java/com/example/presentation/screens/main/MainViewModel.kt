package com.example.presentation.screens.main

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.app.App
import com.example.model.bd.AppDataBase
import com.example.model.entity.Article
import com.example.model.netWork.getDataNet
import com.example.presentation.R
import com.example.presentation.base.BaseRouter
import com.example.presentation.base.BaseViewModel
import com.example.presentation.screens.main.rv_adapter.AdaptRV
import kotlinx.coroutines.*
import org.jetbrains.annotations.TestOnly
import org.xmlpull.v1.XmlPullParser
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainViewModel : BaseViewModel<BaseRouter>() {
    var isLoading = ObservableBoolean(false)
    val adaptRV = AdaptRV()
    lateinit var listArticleLiveData: LiveData<List<Article>>
    var sortByDate = true

    @Inject
    lateinit var db: AppDataBase

    init {
        App.appComponent.inject(this)
    }

    override fun onCreate() {
        listArticleLiveData = db.getArticleDao().getAll().asLiveData()
    }

    fun sort() {
        sortByDate = !sortByDate
        listArticleLiveData.value?.let { setDataRV(it) }
    }

    fun setDataRV(list: List<Article>) {
        if (list.isEmpty())
            loadData()
        if (sortByDate) {
            adaptRV.setArticles(list.sortedByDescending { it.date })

        } else
            adaptRV.setArticles(list.sortedBy { it.date })
    }

    fun loadData() {
        isLoading.set(true)
        viewModelScope.launch(CoroutineExceptionHandler { context, exception ->
            log(exception.message.toString())
            if (exception is IOException)
                Toast.makeText(router?.activity, "Проверьте соединение с интернетом!", Toast.LENGTH_SHORT).show()
        }) {
            isLoading.set(false)
            checkMatches(getDataNet())

        }
    }

    private suspend fun checkMatches(listArticle: List<Article>) {
        listArticleLiveData.value?.let {
            for (article in listArticle) {
                if (article !in listArticleLiveData.value!!) {
                    addArticleBD(article)
                }
            }
        }

    }

     suspend fun addArticleBD(article: Article) {
        db.getArticleDao().insert(article)
    }
}