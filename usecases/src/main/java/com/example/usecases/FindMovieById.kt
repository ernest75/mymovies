package com.example.usecases

import com.example.data.repository.MoviesRepository
import com.example.domain.Movie

class FindMovieById(private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}
