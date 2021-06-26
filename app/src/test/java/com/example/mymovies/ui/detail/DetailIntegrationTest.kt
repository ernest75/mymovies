package com.example.mymovies.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.data.source.LocalDataSource
import com.example.mymovies.ui.FakeLocalDataSource
import com.example.mymovies.ui.defaultFakeMovies
import com.example.mymovies.ui.detail.DetailViewModel.UiModel
import com.example.mymovies.ui.initMockedDi
import com.example.testshared.mockedMovie
import com.example.testshared.mockedMovieFavourite
import com.example.usecases.FindMovieById
import com.example.usecases.ToggleMovieFavorite
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<UiModel>

    private lateinit var vm: DetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> DetailViewModel(id, get(), get(), get()) }
            factory { FindMovieById(get()) }
            factory { ToggleMovieFavorite(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf(1) }

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = defaultFakeMovies
    }

    @After
    fun tearDown(){
        defaultFakeMovies.set(0,mockedMovie.copy(1))
    }

    @Test
    fun `observing LiveData finds the movie`() {

        vm.model.observeForever(observer)

        verify(observer).onChanged(UiModel(mockedMovie.copy(1)))

    }

    @Test
    fun `favorite is updated in local data source`() {

        vm.model.observeForever(observer)

        vm.onFavoriteClicked()

        runBlocking {
            assertTrue(localDataSource.findById(1).favorite)
        }
    }

    @Test
    fun `favorite is deleted in local data source`() {
        vm.model.observeForever(observer)

        defaultFakeMovies.set(0,mockedMovieFavourite.copy(1))

        runBlocking {
            vm.onFavoriteClicked()
            assertTrue(localDataSource.findById(1).favorite)
        }

    }
}