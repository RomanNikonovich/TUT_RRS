package com.example.model.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.model.entity.Article
import kotlinx.coroutines.flow.Flow

const val GET_ALL = "SELECT * FROM TUT_Articles"

@Dao
interface ArticleDao {

    @Query(GET_ALL)
    fun getAll(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: List<Article>)
}