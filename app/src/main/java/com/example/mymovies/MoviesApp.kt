package com.example.mymovies

import android.app.Application
import com.example.mymovies.ui.common.HyperlinkedDebugTree
import timber.log.Timber

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initDI()
        if(BuildConfig.DEBUG){
            Timber.plant(HyperlinkedDebugTree())
        }
    }
}