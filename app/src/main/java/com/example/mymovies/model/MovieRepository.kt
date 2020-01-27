package com.example.mymovies.model

import android.app.Activity
import android.app.Application
import com.example.mymovies.R

class MovieRepository(application: Application) {

    private val apiKey = application.getString(R.string.api_key)
    private val regionRepository = RegionRepository(application)

    suspend fun findPopularMovies() =
        MovieDb.service
            .listPopularMoviesAsync(
                apiKey,
                regionRepository.findLastRegion()
            ).await()

}
