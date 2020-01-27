package com.example.mymovies.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovies.model.Movie
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.common.Event
import com.example.mymovies.ui.common.Scope
import com.example.mymovies.ui.main.MainViewModel.UiModel.*
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel(),
    Scope by Scope.Impl() {

    sealed class UiModel{
        object Loading : UiModel()
        class Content(val movies: List<Movie>) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<Movie>>()
    val navigation : LiveData<Event<Movie>> = _navigation

    init {
        initScope()
    }

  private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = Loading
            _model.value = Content(movieRepository.findPopularMovies().results)
        }

    }

    fun onMovieClicked(movie: Movie) {
       _navigation.value = Event(movie)
    }

    override fun onCleared() {
        cancelScope()
    }


}