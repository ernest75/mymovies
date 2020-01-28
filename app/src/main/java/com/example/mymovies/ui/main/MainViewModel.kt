package com.example.mymovies.ui.main
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovies.database.Movie
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.common.Event
import com.example.mymovies.ui.common.Scope
import com.example.mymovies.ui.common.ScopedViewModel
import com.example.mymovies.ui.main.MainViewModel.UiModel.*
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository) : ScopedViewModel() {

    sealed class UiModel{
        object Loading : UiModel()
        class Content(val movies: List<Movie>) : UiModel()
        class Navigation(val movie: Movie) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    init {
        initScope()
    }

  private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = Loading
            _model.value = Content(movieRepository.findPopularMovies())
        }

    }

    fun onMovieClicked(movie: Movie) {
        _model.value = UiModel.Navigation(movie)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }


}