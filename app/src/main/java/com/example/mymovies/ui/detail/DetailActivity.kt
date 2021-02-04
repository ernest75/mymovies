package com.example.mymovies.ui.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.mymovies.R
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.common.Constants.MOVIE
import com.example.mymovies.ui.common.app
import com.example.mymovies.ui.common.getViewModel
import com.example.mymovies.ui.common.loadUrl
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.view_movie.*

class DetailActivity : AppCompatActivity(){

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel = getViewModel {
            DetailViewModel(intent.getIntExtra(MOVIE, -1), MovieRepository(app))
        }

        viewModel.model.observe(this, Observer(::updateUi))

        movieDetailFavorite.setOnClickListener{viewModel.onFavouriteClicked()}

    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(model.movie) {
        movieDetailToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        movieDetailToolbar.setNavigationOnClickListener{onBackPressed()}
        movieDetailToolbar.title = title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
        movieDetailSummary.text = overview
        movieDetailInfo.setMovie(this)
        collapsingBar.title = title
        collapsingBar.setCollapsedTitleTextColor(Color.WHITE)
        collapsingBar.setExpandedTitleColor(Color.WHITE)
        val icon = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        movieDetailFavorite.setImageDrawable(getDrawable(icon))

    }
}

