package com.example.applicationreachtest.dagger.injection

import com.example.applicationreachtest.dagger.NetworkModule
import com.example.applicationreachtest.ui.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * @Author: Dilip
 * @Date: 15/10/21
 */

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param mainActivityViewModel MainActivityViewModel in which to inject the dependencies
     */

    fun inject(mainActivityViewModel: MainActivityViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }
}