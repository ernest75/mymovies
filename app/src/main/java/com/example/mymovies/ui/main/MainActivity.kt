package com.example.mymovies.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.View
import android.webkit.PermissionRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.example.mymovies.R
import com.example.mymovies.model.Movie
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.PermissionRequester
import com.example.mymovies.ui.detail.DetailActivity
import com.example.mymovies.ui.adapters.MoviesAdapter
import com.example.mymovies.ui.getViewModel
import com.example.mymovies.ui.main.MainViewModel.*
import com.example.mymovies.ui.main.MainViewModel.UiModel.*
import com.example.mymovies.ui.startActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter : MoviesAdapter
    private val coarsePermissionChecker = PermissionRequester(this,ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel { MainViewModel(MovieRepository(application)) }

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter

        viewModel.model.observe(this, Observer (::updateUi))
    }

    private fun updateUi(model: UiModel){
        progress.visibility = if (model == Loading) View.VISIBLE else View.GONE

        when(model){
            is Content -> adapter.movies = model.movies
            is Navigation -> startActivity<DetailActivity>{
                putExtra(DetailActivity.MOVIE,model.movie)
            }
            RequestLocationPermission -> coarsePermissionChecker.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }

}
