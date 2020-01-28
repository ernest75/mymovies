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

    class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()
            return _model
        }

    private fun findMovie() {
        launch {
            _model.value = UiModel(moviesRepository.findById(movieId))
        }
    }

}