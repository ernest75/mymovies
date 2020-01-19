package com.example.mymovies.ui.main

import com.example.mymovies.model.Movie
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.common.Scope
import kotlinx.coroutines.launch

class MainPresenter(private val movieRepository: MovieRepository) : Scope by Scope.Impl() {

    interface View{
        fun updateData(movies: List<Movie>)
        fun navigateTo(movie: Movie)
        fun showProgress()
        fun hideProgress()
    }

    private var view: View? = null

    fun onCreate(view: View) {
        this.view = view
        initScope()
        launch {
            view.showProgress()
            view.updateData(movieRepository.findPopularMovies().results)
            view.hideProgress()
        }

    }

    fun onDestroy() {
        cancelScope()
        this.view = null

    }

    fun onMovieClicked(movie: Movie) {
        view?.navigateTo(movie)
    }

}