package com.example.mymovies.data.server

import com.example.data.source.RemoteDataSource
import com.example.domain.Movie
import com.example.mymovies.data.toDomainMovie


class TheMovieDbDataSource(private val theMovieDb: TheMovieDb) : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        theMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }
}