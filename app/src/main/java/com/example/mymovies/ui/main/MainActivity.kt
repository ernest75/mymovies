package com.example.mymovies.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.example.mymovies.R
import com.example.mymovies.model.Movie
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.detail.DetailActivity
import com.example.mymovies.ui.adapters.MoviesAdapter
import com.example.mymovies.ui.main.MainViewModel.*
import com.example.mymovies.ui.main.MainViewModel.UiModel.*
import com.example.mymovies.ui.startActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter : MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this,
            MainViewModelFactory(MovieRepository(this))
        )[MainViewModel::class.java]

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter

        viewModel.model.observe(this, Observer (::updateUi))
    }

    private fun updateUi(model: MainViewModel.UiModel){
        progress.visibility = if (model == Loading) View.VISIBLE else View.GONE

        when(model){
            is Content -> adapter.movies = model.movies
            is Navigation -> startActivity<DetailActivity>{
                putExtra(DetailActivity.MOVIE,model.movie)
            }
        }
    }

}
