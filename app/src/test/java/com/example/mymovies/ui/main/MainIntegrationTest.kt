package com.example.mymovies.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.source.LocalDataSource
import com.example.mymovies.ui.FakeLocalDataSource
import com.example.mymovies.ui.defaultFakeMovies
import com.example.mymovies.ui.initMockedDi
import com.example.mymovies.ui.main.MainViewModel.*
import com.example.testshared.mockedMovie
import com.example.usecases.GetPopularMovies
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.component.get
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTest : AutoCloseKoinTest(){

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<UiModel>

    @Mock
    lateinit var vm: MainViewModel

    @Before
    fun setup(){
        val vmModule= module {
            factory { MainViewModel(get(),get()) }
            factory { GetPopularMovies(get()) }
        }
        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `data is loaded from server when local source is empty`() {

        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested()

        verify(observer).onChanged(UiModel.Content(defaultFakeMovies))

    }

    @Test
    fun `data is loaded from local source when available`() {
        val fakeMovies = listOf(mockedMovie.copy(14),mockedMovie.copy(15))
        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = fakeMovies

        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested()

        verify(observer).onChanged(UiModel.Content(fakeMovies))
    }

}