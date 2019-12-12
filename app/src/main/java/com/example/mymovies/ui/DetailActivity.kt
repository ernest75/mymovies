package com.example.mymovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.bold
import com.example.mymovies.R
import com.example.mymovies.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        with(intent.getParcelableExtra<Movie>(MOVIE)) {
            movieDetailToolbar.title = title
            movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")

            movieDetailSummary.text = overview

            movieDetailInfo.text = androidx.core.text.buildSpannedString {

                bold { append("Original language: ") }
                appendln(originalLanguage)

                bold { append("Original title: ") }
                appendln(originalTitle)

                bold { append("Release date: ") }
                appendln(releaseDate)

                bold { append("Popularity: ") }
                appendln(popularity.toString())

                bold { append("Vote Average: ") }
                append(voteAverage.toString())
            }
        }
    }
}
