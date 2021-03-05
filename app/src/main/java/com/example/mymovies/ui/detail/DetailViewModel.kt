package com.example.mymovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.database.Movie
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.common.ScopedViewModel
import kotlinx.coroutines.launch


class DetailViewModel (private val movieId: Int, private val moviesRepository: MovieRepository) :ScopedViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String> get() = _overview

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> get() = _favorite

    init {
        launch {
            _movie.value = moviesRepository.findById(movieId)
            updateUi()
        }
    }

    fun onFavoriteClicked() {
        launch {
            movie.value?.let {
                val updatedMovie = it.copy(favorite = !it.favorite)
                _movie.value = updatedMovie
                updateUi()
                moviesRepository.updateMovie(updatedMovie)
            }
        }
    }

    private fun updateUi() {
        movie.value?.run {
            _title.value = title
            _overview.value = overview
            _favorite.value = favorite
        }
    }


}