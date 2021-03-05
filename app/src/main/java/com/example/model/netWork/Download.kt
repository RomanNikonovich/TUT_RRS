package com.example.model.netWork

import com.example.model.entity.Article
import com.example.presentation.screens.main.log
import kotlinx.coroutines.*
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import java.net.URL

suspend fun getDataNet(
    url: String = "https://news.tut.by/rss/index.rss",
    sax: DefaultHandler = MySAX()
): List<Article> {
    return withContext(Dispatchers.IO) {
             CreateXmlReader.xmlReader.contentHandler = sax
             CreateXmlReader.xmlReader.parse(InputSource(URL(url).openStream()))
             if (sax is MySAX)
                 sax.listArticle
             else
                 arrayListOf()

     }
}