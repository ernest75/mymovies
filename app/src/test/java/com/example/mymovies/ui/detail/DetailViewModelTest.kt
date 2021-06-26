package com.example.mymovies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testshared.mockedMovie
import com.example.usecases.FindMovieById
import com.example.usecases.ToggleMovieFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findMovieById:FindMovieById

    @Mock
    lateinit var toogleMovieFavorite: ToggleMovieFavorite

    @Mock
    lateinit var observer: Observer<Any>

    private lateinit var vm: DetailViewModel

    @Before
    fun setup() {
        vm =
            DetailViewModel(1, findMovieById, toogleMovieFavorite, Dispatchers.Unconfined)

    }

    @Test
    fun `observing liveData finds the movie`(){
        runBlocking {
            val movie = mockedMovie.copy(id = 1)

            whenever(findMovieById.invoke(1)).thenReturn(movie)

            vm.model.observeForever(observer)

            verify(observer).onChanged(DetailViewModel.UiModel(movie))
        }
    }

    @Test
    fun `when favourite clicked then tooglemovie favourite invoke`(){
        runBlocking {
            val movie = mockedMovie.copy(1)
            whenever(findMovieById.invoke(1)).thenReturn(movie)
            whenever(toogleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))

            vm.model.observeForever(observer)
            vm.onFavoriteClicked()

            verify(toogleMovieFavorite).invoke(movie)
        }
    }

    @Test
    fun `when favourite clicked value changes`(){
        runBlocking {
            val favorite = false
            val movie = mockedMovie.copy(1,favorite = favorite)
            whenever(findMovieById.invoke(1)).thenReturn(movie)
            whenever(toogleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))

            vm.model.observeForever(observer)
            vm.onFavoriteClicked()

            assertEquals(movie.favorite,favorite)
        }
    }


}