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