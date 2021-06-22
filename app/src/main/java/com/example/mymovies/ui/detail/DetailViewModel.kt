package com.example.mymovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.Movie

import com.example.mymovies.ui.common.ScopedViewModel
import com.example.usecases.FindMovieById
import com.example.usecases.ToggleMovieFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailViewModel (private val movieId: Int,
                       private val findMovieById: FindMovieById,
                       private val toggleMovieFavorite: ToggleMovieFavorite
) :ScopedViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() {
            if (_movie.value == null) findMovie()
            return _movie
        }

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String> get() = _overview

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> get() = _favorite

    fun onFavoriteClicked() {
        launch {
            movie.value?.let {
                _movie.value = toggleMovieFavorite.invoke(it)
                updateUi()

            }
        }
    }

    private fun findMovie() {
        launch {
            _movie.value = findMovieById.invoke(movieId)
            updateUi()
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