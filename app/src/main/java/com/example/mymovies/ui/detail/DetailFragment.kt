package com.example.mymovies.ui.detail

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.data.repository.MoviesRepository
import com.example.data.repository.RegionRepository
import com.example.mymovies.R
import com.example.mymovies.data.AndroidPermissionChecker
import com.example.mymovies.data.PlayServicesLocationDataSource
import com.example.mymovies.data.database.RoomDataSource
import com.example.mymovies.data.server.TheMovieDbDataSource
import com.example.mymovies.databinding.FragmentDetailBinding
import com.example.mymovies.ui.common.*
import com.example.usecases.FindMovieById
import com.example.usecases.ToggleMovieFavorite
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment() : Fragment() {

    private val START_ANIMATION_POSITION = 0
    private val END_ANIMATION_POSITION = -1000
    private val DURATION_ANIMATION = 1200L

    private val viewModel by lazy { getViewModel { component.detaiViewModel } }
    private lateinit var component: DetailFragmentComponent
    private var binding: FragmentDetailBinding? = null
    private val args: DetailFragmentArgs by navArgs()
    private var valueAnimator:ValueAnimator = ValueAnimator.ofInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (valueAnimator.isRunning){
                    valueAnimator.cancel()
                }
                isEnabled = false
                activity?.onBackPressed()
            }
        })
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_detail, false)

        binding!!.movieDetailToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding!!.movieDetailToolbar.setNavigationOnClickListener{ activity?.onBackPressed() }
        binding!!.collapsingBar.setCollapsedTitleTextColor(Color.WHITE)
        binding!!.collapsingBar.setExpandedTitleColor(Color.WHITE)


        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        component = app.component.plus(DetailFragmentModule(args.id))

        binding!!.movieDetailImage.apply {
            transitionName = args.uri
            Glide.with(this)
                .load(transitionName)
                .into(this)
        }

        animateCollapsingAppBar(START_ANIMATION_POSITION,END_ANIMATION_POSITION,DURATION_ANIMATION)

        binding!!.appBarLayout.setOnClickListener {
            valueAnimator.cancel()
        }

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@DetailFragment
        }
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