package com.example.mymovies.model

import android.app.Application
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}

class PlayServiceLocationDataSource(application: Application) : LocationDataSource{
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    override suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }

    }


}