package com.example.mymovies.di

import android.app.Application
import com.example.mymovies.ui.detail.DetailFragmentComponent
import com.example.mymovies.ui.detail.DetailFragmentModule
import com.example.mymovies.ui.main.MainFragmentComponent
import com.example.mymovies.ui.main.MainFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MyMoviesComponent {

    fun plus(module: MainFragmentModule): MainFragmentComponent
    fun plus(module: DetailFragmentModule) : DetailFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MyMoviesComponent
    }
}