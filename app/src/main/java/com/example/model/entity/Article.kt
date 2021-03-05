package com.example.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "TUT_Articles")
data class Article(val title: String, val date: Long, val author: String) {

    var text: String = ""

    @PrimaryKey
    var hashId = this.hashCode()

    var isClicked = false

}
