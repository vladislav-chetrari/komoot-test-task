package com.komoot.vchetrari.challenge.data

import com.komoot.vchetrari.challenge.data.model.UserLocation
import com.komoot.vchetrari.challenge.data.source.UserLocationDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocationRepository @Inject constructor(
    private val userLocationDataSource: UserLocationDataSource
) {

    private val isTrackingEnabledStateFlow = MutableStateFlow(false)

    fun flowByDistance(distanceMeters: Int): Flow<UserLocation> = userLocationDataSource(distanceMeters)

    fun isTrackingEnabled(): Flow<Boolean> = isTrackingEnabledStateFlow

    fun updateTrackingEnabled(value: Boolean) {
        isTrackingEnabledStateFlow.update { value }
    }
}