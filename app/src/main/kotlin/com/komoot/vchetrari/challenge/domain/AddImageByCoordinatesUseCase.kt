package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.PhotoRepository
import com.komoot.vchetrari.challenge.data.model.UserLocation
import javax.inject.Inject

class AddImageByCoordinatesUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(userLocation: UserLocation) {
        val photo = repository.getByLocation(userLocation)
        if (photo != null) {
            repository.save(photo)
        }
    }
}