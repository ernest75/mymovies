package com.example.mymovies.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.Movie
import com.example.mymovies.R
import com.example.mymovies.ui.common.Constants
import com.example.mymovies.ui.common.inflate
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.view_movie.view.*

class MoviesAdapter() :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<Movie> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition] == new[newItemPosition]

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition].id == new[newItemPosition].id

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_movie, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieImageView:ImageView = itemView.movieCover
        private val llRvItemMovie :LinearLayout = itemView.ll_movie_view_rv

        fun bind(movie: Movie) {
            itemView.movieTitle.text = movie.title
            itemView.movieCover.apply {
                transitionName = Constants.BASE_URL_PATH + movie.posterPath
                Glide.with(context)
                    .load(transitionName)
                    .into(this)
            }

            llRvItemMovie.setOnClickListener {
                movieSelectedListener.onMovieSelected(movie, movieImageView)
            }
        }
    }

    interface MovieSelectedListener {
        fun onMovieSelected(movie: Movie, imageView: ImageView)
    }
    lateinit var movieSelectedListener: MoviesAdapter.MovieSelectedListener
}
