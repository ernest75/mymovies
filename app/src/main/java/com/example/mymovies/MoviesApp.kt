package com.example.mymovies

import android.app.Application
import androidx.room.Room
import com.example.mymovies.data.database.MovieDatabase
import com.example.mymovies.di.DaggerMyMoviesComponent
import com.example.mymovies.di.MyMoviesComponent
import com.example.mymovies.ui.common.Constants
import com.example.mymovies.ui.common.HyperlinkedDebugTree
import timber.log.Timber

class MoviesApp : Application() {

    lateinit var component: MyMoviesComponent
        private set
    
    override fun onCreate() {
        super.onCreate()

        component = DaggerMyMoviesComponent
            .factory()
            .create(this)

        if(BuildConfig.DEBUG){
            Timber.plant(HyperlinkedDebugTree())
        }
    }
}