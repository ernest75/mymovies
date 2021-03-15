package com.example.mymovies.ui.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Movie
import com.example.mymovies.R

import com.example.mymovies.ui.adapters.MoviesAdapter
import com.example.mymovies.ui.common.loadUrl
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}

@BindingAdapter("items")
fun RecyclerView.setItems(movies: List<Movie>?) {
    (adapter as? MoviesAdapter)?.let {
        it.movies = movies ?: emptyList()
    }
}

@BindingAdapter("movie")
fun TextView.updateMovieDetails(movie: Movie?) = movie?.run {
    text = buildSpannedString {

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

@BindingAdapter("favorite")
fun FloatingActionButton.setFavorite(favorite: Boolean?) {
    val icon = if (favorite == true) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
    setImageDrawable(ContextCompat.getDrawable(context, icon))
}
