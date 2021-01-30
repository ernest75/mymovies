package com.example.mymovies.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.mymovies.R
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.PermissionRequester
import com.example.mymovies.ui.detail.DetailActivity
import com.example.mymovies.ui.adapters.MoviesAdapter
import com.example.mymovies.ui.common.app
import com.example.mymovies.ui.common.getViewModel
import com.example.mymovies.ui.main.MainViewModel.*
import com.example.mymovies.ui.main.MainViewModel.UiModel.*
import com.example.mymovies.ui.common.startActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private lateinit var adapter : MoviesAdapter
    private val coarsePermissionChecker =
        PermissionRequester(this, ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel { MainViewModel(MovieRepository(app)) }

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter

        viewModel.model.observe(this, Observer (::updateUi))

    }

    private fun updateUi(model: UiModel) {

        progress.visibility = if (model is UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is UiModel.Content -> adapter.movies = model.movies
            is UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, model.movie.id)
            }
            UiModel.RequestLocationPermission -> coarsePermissionChecker.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }



}
