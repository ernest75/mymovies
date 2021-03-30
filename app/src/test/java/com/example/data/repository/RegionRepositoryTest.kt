package com.example.data.repository

import com.example.data.repository.PermissionChecker.*
import com.example.data.source.LocationDataSource
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
class RegionRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker


    lateinit var regionRepository: RegionRepository

    @Before
    fun setup() {
        regionRepository = RegionRepository(locationDataSource,permissionChecker)

    }

    @Test
    fun `return default region when permission not granted`(){
         runBlocking {

             whenever(permissionChecker.check(Permission.COARSE_LOCATION)).thenReturn(false)

             val region = regionRepository.findLastRegion()

             assertEquals(RegionRepository.DEFAULT_REGION,region)
         }
    }

    @Test
    fun `returns correct region when permission granted`(){
        runBlocking {

            whenever(permissionChecker.check(Permission.COARSE_LOCATION)).thenReturn(true)
            whenever(regionRepository.findLastRegion()).thenReturn("US")

            val region = regionRepository.findLastRegion()

            assertEquals("US",region)
        }
    }





}