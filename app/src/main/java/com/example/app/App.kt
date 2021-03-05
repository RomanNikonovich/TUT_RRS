package com.example.app

import android.app.Application
import com.example.injection.AppComponent
import com.example.injection.AppModule
import com.example.injection.DaggerAppComponent


class App : Application() {
    companion object{
        lateinit var appComponent : AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}