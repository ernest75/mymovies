package com.example.mymovies.ui.detail

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.mymovies.R
import com.example.mymovies.databinding.ActivityDetailBinding
import com.example.mymovies.ui.common.*
import com.example.mymovies.ui.detail.DetailActivity.AnimationConstants.DURATION_ANIMATION
import com.example.mymovies.ui.detail.DetailActivity.AnimationConstants.END_ANIMATION_POSITION
import com.example.mymovies.ui.detail.DetailActivity.AnimationConstants.START_ANIMATION_POSITION
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.scope.ScopeActivity
import org.koin.core.parameter.parametersOf


class DetailActivity : ScopeActivity()  {

    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    object AnimationConstants{
        const val START_ANIMATION_POSITION = 0
        const val END_ANIMATION_POSITION = -1000
        const val DURATION_ANIMATION = 1200L
    }

    private var valueAnimator:ValueAnimator = ValueAnimator.ofInt()

    private val viewModel: DetailViewModel by inject{
        parametersOf(intent.getIntExtra(MOVIE, -1))
    }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.model.observe(this, Observer(::updateUi))
        setupToolbar()

        binding.movieDetailFavorite.setOnClickListener { viewModel.onFavoriteClicked() }

        binding.movieDetailToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.movieDetailToolbar.setNavigationOnClickListener{ onBackPressed() }
        binding.collapsingBar.setCollapsedTitleTextColor(Color.WHITE)
        binding.collapsingBar.setExpandedTitleColor(Color.WHITE)
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(binding) {
        val movie = model.movie
        movieDetailToolbar.title = movie.title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780${movie.backdropPath}")
        movieDetailSummary.text = movie.overview
        movieDetailInfo.setMovie(movie)

        val icon = if (movie.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        movieDetailFavorite.setImageDrawable(ContextCompat.getDrawable(this@DetailActivity, icon))
    }

    private fun setupToolbar() {
        animateCollapsingAppBar(
            START_ANIMATION_POSITION,
            END_ANIMATION_POSITION,
            DURATION_ANIMATION
        )
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (valueAnimator.isRunning) {
                    valueAnimator.cancel()
                }
                isEnabled = false
                onBackPressed()
            }
        })
    }

    private fun animateCollapsingAppBar(startPosition:Int, endPosition:Int, duration:Long) {
        val params: CoordinatorLayout.LayoutParams =
            appBarLayout.getLayoutParams() as CoordinatorLayout.LayoutParams
        appBarCordinator.layoutParams
        params.behavior = AppBarLayout.Behavior()
        val behavior: AppBarLayout.Behavior = params.behavior as AppBarLayout.Behavior
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            behavior.topAndBottomOffset = (animation.animatedValue as Int)
            appBarLayout.requestLayout()
        }
        valueAnimator.setIntValues(startPosition, endPosition)
        valueAnimator.duration = duration
        valueAnimator.start()
    }

}