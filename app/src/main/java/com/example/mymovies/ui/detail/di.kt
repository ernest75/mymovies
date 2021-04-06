package com.example.mymovies.ui.detail

import com.example.data.repository.MoviesRepository
import com.example.usecases.FindMovieById
import com.example.usecases.ToggleMovieFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
class DetailFragmentModule(private val movieId: Int) {

    @Provides
    fun detailViewModelProvider(

        findMovieById: FindMovieById,
        toggleMovieFavorite: ToggleMovieFavorite,
        uiDispatcher: CoroutineDispatcher
    ): DetailViewModel {
        return DetailViewModel(movieId, findMovieById, toggleMovieFavorite,uiDispatcher)
    }

    @Provides
    fun findMovieByIdProvider(moviesRepository: MoviesRepository) = FindMovieById(moviesRepository)

    @Provides
    fun toggleMovieFavoriteProvider(moviesRepository: MoviesRepository) =
        ToggleMovieFavorite(moviesRepository)
}

@Subcomponent(modules = [(DetailFragmentModule::class)])
interface DetailFragmentComponent {
    val detaiViewModel: DetailViewModel
}