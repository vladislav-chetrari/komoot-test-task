package com.komoot.vchetrari.challenge.data.source

import com.komoot.vchetrari.challenge.data.model.Photo
import com.komoot.vchetrari.challenge.data.model.UserLocation
import javax.inject.Inject

class PhotoByUserLocationDataSource @Inject constructor(
    private val photoSearchClient: PhotoSearchClient
) {

    suspend operator fun invoke(userLocation: UserLocation): Photo? {
        val distanceFromLastLocationKm = userLocation.distanceFromLastLocationMeters.toDouble() / 1000
        val radiusKm = distanceFromLastLocationKm / 2
        val response = photoSearchClient.getByCoordinates(
            userLocation.latitude,
            userLocation.longitude,
            radiusKm
        )
        return response.photos.value.firstOrNull()?.let {
            Photo(
                id = it.id,
                timestampMillis = System.currentTimeMillis(),
                url = "https://live.staticflickr.com/${it.serverId}/${it.id}_${it.secret}_b.jpg",
            )
        }
    }
}