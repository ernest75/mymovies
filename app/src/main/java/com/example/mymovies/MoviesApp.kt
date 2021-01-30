package com.example.mymovies

import android.app.Application
import androidx.room.Room
import com.example.mymovies.database.MovieDatabase
import com.example.mymovies.ui.common.HyperlinkedDebugTree
import com.facebook.stetho.Stetho
import timber.log.Timber

class MoviesApp : Application() {

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie-db"
        ).build()

        Stetho.initializeWithDefaults(this)

        if(BuildConfig.DEBUG){
            Timber.plant(HyperlinkedDebugTree())
        }

    }
}