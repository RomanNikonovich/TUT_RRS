package com.example.presentation.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<R : BaseRouter> : ViewModel() {
    protected var router: R? = null

    fun attach(router: R) {
        this.router = router
    }

    fun detach() {
        this.router = null
    }

    open fun onCreate() {}

    open fun onResume() {}

    open fun onPause() {}

    open fun onStart() {}

    open fun onStop() {}

    open fun onDestroy() {}
}