package com.example.mymovies.ui.main


import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.mymovies.R
import com.example.mymovies.databinding.FragmentMainBinding
import com.example.mymovies.ui.adapters.MoviesAdapter
import com.example.mymovies.ui.common.*
import com.example.usecases.GetPopularMovies
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.view_movie.*
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private val viewModel: MainViewModel = getViewModel()
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

        viewModel.navigateToMovie.observe(viewLifecycleOwner, EventObserver { navigationEvent ->
            val pass = "https://image.tmdb.org/t/p/w185/${navigationEvent.movie.posterPath}"
            val extras = FragmentNavigatorExtras(
                navigationEvent.imageView to pass
            )
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(navigationEvent.movie.id,pass)
            navController.navigate(action,extras)
        })

        viewModel.requestLocationPermission.observe(viewLifecycleOwner, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainFragment
        }
    }
}
