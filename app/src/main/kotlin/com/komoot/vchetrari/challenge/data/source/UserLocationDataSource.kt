package com.komoot.vchetrari.challenge.data.source

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.komoot.vchetrari.challenge.data.model.UserLocation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserLocationDataSource @Inject constructor(
    private val looper: Looper,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) {

    @SuppressLint("MissingPermission")
    @Throws(SecurityException::class)
    operator fun invoke(distanceMeters: Int): Flow<UserLocation> = callbackFlow {
        val locationRequest = LocationRequest.Builder(DEFAULT_INTERVAL_MILLIS)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMinUpdateDistanceMeters(distanceMeters.toFloat())
            .build()
        val locationListener = LocationListener { location ->
            launch {
                send(UserLocation(location.latitude, location.longitude, distanceMeters))
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationListener, looper)
        awaitClose { fusedLocationProviderClient.removeLocationUpdates(locationListener) }
    }

    private companion object {
        const val DEFAULT_INTERVAL_MILLIS = 10_000L
    }
}