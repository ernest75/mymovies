package com.example.mymovies

import android.app.Application
import androidx.room.Room
import com.example.mymovies.database.MovieDatabase
import com.facebook.stetho.Stetho

class MoviesApp : Application() {

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie-db"
        ).build()

        Stetho.initializeWithDefaults(this);
    }
}