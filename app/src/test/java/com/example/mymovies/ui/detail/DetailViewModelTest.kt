package com.example.mymovies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mymovies.CoroutinesTestRule
import com.example.mymovies.MoviesApp
import com.example.mymovies.mockedMovie
import com.example.mymovies.model.MovieRepository
import com.example.mymovies.ui.main.MainViewModel
import com.nhaarman.mockitokotlin2.whenever
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
import org.mockito.Mockito

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()


    @Mock
    lateinit var movieRepo: MovieRepository

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    private lateinit var vm: DetailViewModel

    @Before
    fun setup() {
        vm = DetailViewModel(1,movieRepo)

    }

    @Test
    fun `observing LiveData finds the movie`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(movieRepo.findById(Mockito.anyInt())).thenReturn(movie)


            vm.model.observeForever(observer)
            vm.findMovie()

            verify(observer).onChanged(DetailViewModel.UiModel(movie))
        }
    }

    // region helper methods -----------------------------------------------------------------------

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------

    // end region helper classes -------------------------------------------------------------------


}