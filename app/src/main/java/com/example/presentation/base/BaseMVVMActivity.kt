package com.example.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.presentation.BR

abstract class BaseMVVMActivity<Binding : ViewDataBinding, ViewModel : BaseViewModel<BaseRouter>, R : BaseRouter> : AppCompatActivity() {

    protected lateinit var binding: Binding
    protected lateinit var viewModel: ViewModel
    protected var router: R? = null

    abstract fun provideLayoutId(): Int

    abstract fun provideViewModel(): ViewModel

    abstract fun provideRouter(): R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
        binding = DataBindingUtil.setContentView(this, provideLayoutId())
        binding.setVariable(BR.viewModel, viewModel)
        router = provideRouter()
        viewModel.attach(router!!)
        viewModel.onCreate()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        router = null
        viewModel.detach()
        viewModel.onDestroy()
    }

}