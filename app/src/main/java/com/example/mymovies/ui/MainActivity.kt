package com.example.mymovies.ui

import android.os.Bundle
import com.antonioleiva.mymovies.ui.common.CoroutineScopeActivity
import com.example.mymovies.R
import com.example.mymovies.model.MovieRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch


class MainActivity : CoroutineScopeActivity() {

    private val movieRepository : MovieRepository by lazy{MovieRepository(this)}

    private val adapter = MoviesAdapter {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.MOVIE, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.adapter = adapter


        launch {
            adapter.movies = movieRepository.findPopularMovies().results

        }


    }



}
