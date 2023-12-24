package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.UserLocationRepository
import com.komoot.vchetrari.challenge.data.model.UserLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlowUserLocationUseCase @Inject constructor(
    private val repository: UserLocationRepository
) {

    operator fun invoke(): Flow<UserLocation> = repository
        .flowByDistance(USER_LOCATION_MIN_UPDATE_DISTANCE_METERS)

    private companion object {
        const val USER_LOCATION_MIN_UPDATE_DISTANCE_METERS = 100
    }
}