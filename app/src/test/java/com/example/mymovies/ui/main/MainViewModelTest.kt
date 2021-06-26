package com.example.mymovies.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testshared.mockedMovie
import com.example.usecases.GetPopularMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.isNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


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

        vm.model.observeForever(observer)

        verify(observer).onChanged(vm.model.value)

    }

    @Test
    fun `after requesting the permission loading is shown`(){
        runBlocking {
            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(MainViewModel.UiModel.Loading)
        }
    }

    @Test
    fun `after requesting permission getPopularMovies invoke`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)
            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(MainViewModel.UiModel.Content(movies))

        }
    }

    @Test
    fun `after getting popular movies loading is hide`(){
        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)
            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(MainViewModel.UiModel.Content(movies))

        }
    }



    
}