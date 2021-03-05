package com.example.model.netWork

import com.example.model.entity.Article
import com.example.model.utils.parsePubDate
import com.example.presentation.screens.main.log
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

val simplFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)

class MySAX : DefaultHandler() {

    val listArticle = arrayListOf<Article>()
    private var element: String = ""
    private var tempTitle: String = ""
    private var tempDate: String = ""
    private var tempAuthor: String = ""
    private var tempText: String = ""
    private var isStart = false
    private var isParseText = false

    override fun startDocument() {
        listArticle.clear()
    }

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {

        qName?.let { if (it == "item") isStart = true }
        qName?.let { element = qName }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        val s = ch?.let { String(it, start, length) } ?: ""
        if (isStart) {
            if (s.contains("br clear=\"all\" /"))
                isParseText = false

            if (isParseText) {
                if (!s.contains(">") && !s.contains("<"))
                    tempText = s
            }
            if (s.contains("hspace=\"5\""))
                isParseText = true

            when (element) {
                "title" -> tempTitle = ch?.let { String(it, start, length) } ?: ""
                "pubDate" -> tempDate = ch?.let { String(it, start, length) } ?: ""
                "atom:name" -> tempAuthor = ch?.let { String(it, start, length) } ?: ""
            }
        }

    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {

        element = ""
        if (qName == "item") {
            var date = 0L
            try {
            date = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH).parse(
                tempDate).time
        } catch (se: SAXException) {
            log(se.message.toString())
        } catch (ie: IOException) {
                log(ie.message.toString())
        } catch (oe: Exception) {
                log(oe.message.toString())
        }

            listArticle.add(Article(tempTitle, date, tempAuthor).apply {
                text = tempText
            })
        }
    }

}

