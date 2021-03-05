package com.example.injection

import com.example.presentation.screens.main.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
}