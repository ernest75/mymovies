package com.example.data.repository

import com.example.data.source.LocalDataSource
import com.example.data.source.RemoteDataSource
import com.example.domain.Movie
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
class MoviesRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var moviesRepository: MoviesRepository

    private val apiKey = "123"

    @Before
    fun setup() {
        moviesRepository = MoviesRepository(localDataSource,remoteDataSource,regionRepository,apiKey)

    }

    @Test
    fun `getPopularMovies gets from local data source first`(){
        runBlocking {

            val localMovies = listOf(mockedMovie.copy(1))
            whenever(localDataSource.isEmpty()).thenReturn(false)
            whenever(localDataSource.getPopularMovies()).thenReturn(localMovies)

            val result = moviesRepository.getPopularMovies()

            assertEquals(localMovies,result)

        }
    }

    @Test
    fun `getPopularMovies saves remote data to local `(){
        runBlocking {

            val remoteMovies = listOf(mockedMovie.copy(2))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getPopularMovies(com.nhaarman.mockitokotlin2.any(), com.nhaarman.mockitokotlin2.any())).thenReturn(remoteMovies)
            whenever(regionRepository.findLastRegion()).thenReturn("US")

            moviesRepository.getPopularMovies()

            verify(localDataSource).saveMovies(remoteMovies)

        }
    }



    private val mockedMovie = Movie(
        0,
        "Title",
        "Overview",
        "01/01/2025",
        "",
        "",
        "EN",
        "Title",
        5.0,
        5.1,
        false
    )



}