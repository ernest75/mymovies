package com.example.mymovies

import android.app.Application
import androidx.room.Room
import com.example.mymovies.data.database.MovieDatabase
import com.example.mymovies.ui.common.Constants
import com.example.mymovies.ui.common.HyperlinkedDebugTree
import timber.log.Timber

class MoviesApp : Application() {

    lateinit var db: MovieDatabase
        private set
    
    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, Constants.DB_NAME
        ).build()

        if(BuildConfig.DEBUG){
            Timber.plant(HyperlinkedDebugTree())
        }
    }
}