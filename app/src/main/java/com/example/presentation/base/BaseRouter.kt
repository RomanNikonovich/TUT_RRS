package com.example.presentation.base

import android.app.Activity

abstract class BaseRouter(val activity: Activity){
   open fun back(){}
}