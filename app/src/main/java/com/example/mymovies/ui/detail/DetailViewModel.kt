package com.example.mymovies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.Movie
import com.example.mymovies.ui.common.ScopedViewModel
import com.example.usecases.FindMovieById
import com.example.usecases.ToggleMovieFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch


class DetailViewModel (  private val movieId: Int,
                         private val findMovieById: FindMovieById,
                         private val toggleMovieFavorite: ToggleMovieFavorite,
                         override val uiDispatcher: CoroutineDispatcher
) :ScopedViewModel(uiDispatcher) {

    data class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()
            return _model
        }

    private fun findMovie() = launch {
        _model.value = UiModel(findMovieById.invoke(movieId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.movie?.let {
            _model.value = UiModel(toggleMovieFavorite.invoke(it))
        }
    }

}