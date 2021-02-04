package com.example.mymovies.model

import com.example.mymovies.MoviesApp
import com.example.mymovies.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.mymovies.database.Movie as DbMovie
import com.example.mymovies.model.Movie as ServerMovie

class MovieRepository(application: MoviesApp) {

    private val apiKey = application.getString(R.string.api_key)
    private val regionRepository = RegionRepository(application)
    private val db = application.db

    suspend fun findPopularMovies(): List<DbMovie> = withContext(Dispatchers.IO){
        with(db.movieDao()){
          if (movieCount()<= 0){

              val movies = MovieDb.service
                  .listPopularMoviesAsync(apiKey, regionRepository.findLastRegion())
                  .await()
                  .results
              insertMovies(movies.map { it.convertToDbMovie() })
          }

            getAll()
        }
    }


    suspend fun findById(id: Int): DbMovie = withContext(Dispatchers.IO) {
        db.movieDao().findById(id)
    }

    suspend fun updateMovie(movie: DbMovie) = withContext(Dispatchers.IO){
        db.movieDao().updateMovie(movie)
    }


    private fun ServerMovie.convertToDbMovie()= DbMovie(
        0,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )
}
