package com.example.presentation.screens.show_article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.presentation.R
import com.example.presentation.screens.main.TEXT_KEY

class ShowArticle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_article)
        intent.extras?.let { findViewById<TextView>(R.id.text).text = it.get(TEXT_KEY) as String }

    }
}