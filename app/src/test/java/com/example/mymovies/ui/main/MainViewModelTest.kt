package com.example.mymovies.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mymovies.ui.common.Event
import com.example.testshared.mockedMovie
import com.example.usecases.GetPopularMovies
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var observer: Observer<Any>

    private lateinit var vm: MainViewModel

    @Before
    fun setup() {
        vm = MainViewModel(getPopularMovies,Dispatchers.Unconfined)

    }

    @Test
    fun `on launched location permission requested`(){

        vm.requestLocationPermission.observeForever(observer)

        verify(observer).onChanged(vm.requestLocationPermission.value)

    }

    @Test
    fun `after requesting the permission loading is shown`(){
        runBlocking {
            vm.loading.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(true)
        }
    }

    @Test
    fun `after requesting permission getPopularMovies invoke`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)
            vm.movies.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(movies)

        }
    }

    @Test
    fun `after getting popular movies loading is hide`(){
        runBlocking {
            vm.loading.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(false)

        }
    }
    
}