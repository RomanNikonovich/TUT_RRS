package com.example.injection

import android.content.Context
import androidx.room.Room
import com.example.model.bd.AppDataBase
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun getContextMod() = context

    @Provides
    @Singleton
    fun getAppDataBase(context: Context) =
        Room.databaseBuilder(context, AppDataBase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()

}