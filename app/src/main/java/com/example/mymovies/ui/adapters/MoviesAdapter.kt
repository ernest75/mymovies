package com.example.mymovies.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Movie
import com.example.mymovies.R
import com.example.mymovies.databinding.ViewMovieBinding
import com.example.mymovies.ui.common.basicDiffUtil
import com.example.mymovies.ui.common.inflate
import com.example.mymovies.ui.common.loadUrl

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_movie, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewMovieBinding.bind(view)
        fun bind(movie: Movie) = with(binding) {
            movieTitle.text = movie.title
            movieCover.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
        }
    }
}
