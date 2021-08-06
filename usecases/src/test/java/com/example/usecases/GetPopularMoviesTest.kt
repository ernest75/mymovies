package com.example.usecases

import com.example.data.repository.MoviesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetPopularMoviesTest {

    @Mock
    lateinit var moviesRepository:MoviesRepository

    lateinit var getPopularMovies: GetPopularMovies

    @Before
    fun setup() {
        getPopularMovies = GetPopularMovies(moviesRepository)

    }

    @Test
    fun`use case uses movie repo`(){
        runBlocking {
            val movies = listOf(mockedMovie.copy(1), mockedMovie.copy(2))

            whenever(moviesRepository.getPopularMovies()).thenReturn(movies)

            val result = getPopularMovies.invoke()

            assertEquals(movies,result)
        }
    }



}