package com.example.usecases

import com.example.data.repository.MoviesRepository
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
import org.mockito.Mockito.verify
import org.mockito.ArgumentMatchers.anyString

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