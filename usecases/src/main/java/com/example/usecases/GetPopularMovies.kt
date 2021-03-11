package com.example.usecases

import com.example.data.repository.MoviesRepository
import com.example.domain.Movie

class GetPopularMovies(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(): List<Movie> = moviesRepository.getPopularMovies()
}