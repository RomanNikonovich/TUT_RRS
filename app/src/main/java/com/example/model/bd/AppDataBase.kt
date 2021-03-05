package com.example.model.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.model.entity.Article

@Database(entities = [Article::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}