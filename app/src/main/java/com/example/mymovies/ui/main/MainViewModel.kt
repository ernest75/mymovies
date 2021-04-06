package com.example.mymovies.ui.main
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.Movie
import com.example.mymovies.ui.common.Event
import com.example.mymovies.ui.common.ScopedViewModel
import com.example.usecases.GetPopularMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val getPopularMovies: GetPopularMovies,
                    uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _navigateToMovie = MutableLiveData<Event<NavigateEvent>>()
    val navigateToMovie: LiveData<Event<NavigateEvent>> get() = _navigateToMovie

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> get() = _requestLocationPermission

    init {
        initScope()
        refresh()
    }

  private fun refresh() {
      _requestLocationPermission.value = Event(Unit)
    }

    fun onCoarsePermissionRequested() {
        launch {
            _loading.value = true
            _movies.value = getPopularMovies.invoke()
            _loading.value = false
        }

    }

    fun onMovieClicked(movie: Movie, imageView: ImageView) {
        _navigateToMovie.value = Event(NavigateEvent(movie,imageView))
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }


}

data class NavigateEvent (val movie: Movie,val imageView: ImageView)