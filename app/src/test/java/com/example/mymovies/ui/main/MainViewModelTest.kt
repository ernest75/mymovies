package com.example.mymovies.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import androidx.lifecycle.Observer
import com.example.mymovies.CoroutinesTestRule
import com.example.mymovies.mockedMovie
import com.example.mymovies.model.MovieRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking


import org.hamcrest.CoreMatchers.`is`

import org.hamcrest.CoreMatchers.*


import org.junit.Assert.assertThat
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

    @Mock
    lateinit var movieRepo: MovieRepository

    private lateinit var vm: MainViewModel

    @Before
    fun setup() {
        vm = MainViewModel(movieRepo)

    }

    @Test
    fun `observing LiveData launches location permission request`() {

        vm.model.observeForever(observer)

        verify(observer).onChanged(MainViewModel.UiModel.RequestLocationPermission)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(movieRepo.findPopularMovies()).thenReturn(movies)
            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(MainViewModel.UiModel.Loading)

        }
    }

    @Test
    fun `onPermission is called permision is requested`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(movieRepo.findPopularMovies()).thenReturn(movies)
            vm.model.observeForever(observer)
            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(MainViewModel.UiModel.RequestLocationPermission)

        }

    }

}