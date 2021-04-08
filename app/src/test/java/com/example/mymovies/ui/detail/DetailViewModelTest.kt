package com.example.mymovies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.domain.Movie
import com.example.testshared.mockedMovie
import com.example.usecases.FindMovieById
import com.example.usecases.ToggleMovieFavorite
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import org.mockito.ArgumentMatchers.any
import org.hamcrest.CoreMatchers.`is`

import org.hamcrest.CoreMatchers.*
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.*
import org.mockito.ArgumentCaptor.*
import org.junit.Assert.assertThat
import org.junit.Rule
import org.mockito.Mockito.verify
import org.mockito.ArgumentMatchers.anyString


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

            vm.movie.observeForever(observer)

            verify(observer).onChanged(movie)
        }
    }

    @Test
    fun `when favourite clicked then tooglemovie favourite invoke`(){
        runBlocking {
            val movie = mockedMovie.copy(1)
            whenever(findMovieById.invoke(1)).thenReturn(movie)
            whenever(toogleMovieFavorite.invoke(movie)).thenReturn(movie.copy(favorite = !movie.favorite))

            vm.movie.observeForever(observer)
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

            vm.movie.observeForever(observer)
            vm.onFavoriteClicked()

            assertEquals(movie.favorite,favorite)
        }
    }


}