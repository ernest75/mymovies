package com.example.usecases

import com.example.data.repository.MoviesRepository
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
class ToggleMovieFavoriteTest {

    @Mock
    lateinit var moviesRepository:MoviesRepository

    lateinit var toogleMovieFavorite: ToggleMovieFavorite

    @Before
    fun setup() {
        toogleMovieFavorite = ToggleMovieFavorite(moviesRepository)

    }

    @Test
    fun`invoke calls movie repo`(){
        runBlocking {
            val movie = mockedMovie.copy(1)

            val result = toogleMovieFavorite.invoke(movie)

            verify(moviesRepository).update(result)
        }
    }

    @Test
    fun `not favourite movie becomes favourite`(){
        runBlocking {
            val movie = mockedMovie.copy(1, favorite = false)

            val result = toogleMovieFavorite.invoke(movie)

            assertTrue(result.favorite)

        }
    }

    @Test
    fun `favourite movie becomes not favourite`(){
        runBlocking{
            val movie = mockedMovie.copy(1,favorite = true)

            val result = toogleMovieFavorite.invoke(movie)

            assertFalse(result.favorite)
        }
    }






}