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
class FindMovieByIdTest {

   @Mock
   lateinit var moviesRepository:MoviesRepository

   lateinit var findMovieById: FindMovieById

    @Before
    fun setup() {
        findMovieById = FindMovieById(moviesRepository)

    }

    @Test
    fun`use case uses movie repo to find movie`(){
        runBlocking {
            val movie =  mockedMovie.copy(1)

            whenever(moviesRepository.findById(1)).thenReturn(movie)
            val result = findMovieById.invoke(1)

            assertEquals(movie,result)
        }
    }




}