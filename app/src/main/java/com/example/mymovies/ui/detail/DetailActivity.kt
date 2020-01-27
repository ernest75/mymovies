package com.example.mymovies.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mymovies.R
import com.example.mymovies.model.Movie
import com.example.mymovies.ui.loadUrl
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(){

    private lateinit var viewModel: DetailViewModel

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel = ViewModelProviders.of(
            this,
            DetailViewModel.DetailViewModelFactory(intent.getParcelableExtra(MOVIE)))[DetailViewModel::class.java]

        viewModel.model.observe(this, Observer(::updateUi))

    }


    private fun updateUi(model: DetailViewModel.UiModel) = with(model.movie) {
            movieDetailToolbar.title = title
            movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
            movieDetailSummary.text = overview
            movieDetailInfo.setMovie(this)

        }
    }

