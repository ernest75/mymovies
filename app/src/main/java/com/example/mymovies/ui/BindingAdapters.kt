package com.example.mymovies.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.database.Movie
import com.example.mymovies.ui.adapters.MoviesAdapter
import com.example.mymovies.ui.common.loadUrl

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
