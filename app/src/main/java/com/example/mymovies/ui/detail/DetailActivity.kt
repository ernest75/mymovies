package com.example.mymovies.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.mymovies.R
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.common.app
import com.example.mymovies.ui.common.getViewModel
import com.example.mymovies.ui.common.loadUrl
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(){

    private lateinit var viewModel: DetailViewModel

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

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
            movieDetailToolbar.title = title
            movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
            movieDetailSummary.text = overview
            movieDetailInfo.setMovie(this)

            val icon = if (favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
            movieDetailFavorite.setImageDrawable(getDrawable(icon))

        }
    }

