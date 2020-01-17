package com.example.mymovies.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.mymovies.R
import com.example.mymovies.model.Movie
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.detail.DetailActivity
import com.example.mymovies.ui.adapters.MoviesAdapter
import com.example.mymovies.ui.startActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val presenter by lazy {MainPresenter(MovieRepository(this))}

    private val adapter = MoviesAdapter {
        presenter.onMovieClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onCreate(this)

        recycler.adapter = adapter
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()

    }

    override fun updateData(movies: List<Movie>) {
        adapter.movies = movies
    }

    override fun navigateTo(movie: Movie) {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.MOVIE, movie)
        }
    }

}
