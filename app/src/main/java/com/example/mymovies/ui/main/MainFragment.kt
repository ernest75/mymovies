package com.example.mymovies.ui.main


import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.mymovies.R
import com.example.mymovies.database.Movie
import com.example.mymovies.databinding.FragmentMainBinding
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.adapters.MoviesAdapter
import com.example.mymovies.ui.common.*
import com.example.mymovies.ui.detail.DetailViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.view_movie.*
import timber.log.Timber

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester by lazy {
        PermissionRequester(
            requireActivity(),
            ACCESS_COARSE_LOCATION
        )
    }

    private lateinit var navController: NavController
    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.bindingInflate(R.layout.fragment_main, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        viewModel = getViewModel { MainViewModel(MovieRepository(app)) }

        viewModel.navigateToMovie.observe(viewLifecycleOwner, EventObserver { id ->
            val extras = FragmentNavigatorExtras(movieCover to "imageView")
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(id)
            navController.navigate(action,extras)
        })

        viewModel.requestLocationPermission.observe(viewLifecycleOwner, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })

        adapter = MoviesAdapter()
        adapter.movieSelectedListener = object : MoviesAdapter.MovieSelectedListener{
            override fun onMovieSelected(movie: Movie, imageView: ImageView) {
                val pass = "https://image.tmdb.org/t/p/w185/${movie.posterPath}"
                val extras = FragmentNavigatorExtras(
                    imageView to pass
                )
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment(movie.id,pass)
                findNavController().navigate(action,extras)
            }

        }
        recycler.adapter = adapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainFragment
        }
    }
}
