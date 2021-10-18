package com.example.applicationreachtest.base

import androidx.lifecycle.ViewModel
import com.example.applicationreachtest.dagger.NetworkModule
import com.example.applicationreachtest.dagger.injection.DaggerViewModelInjector
import com.example.applicationreachtest.dagger.injection.ViewModelInjector
import com.example.applicationreachtest.data.CountryList
import com.example.applicationreachtest.ui.MainActivityViewModel
import io.reactivex.disposables.Disposable

/**
 * @Author: Dilip
 * @Date: 17/10/21
 */
abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MainActivityViewModel -> injector.inject(this)
        }
    }
}