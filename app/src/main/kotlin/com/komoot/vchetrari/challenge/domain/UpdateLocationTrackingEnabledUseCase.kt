package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.UserLocationRepository
import javax.inject.Inject

class UpdateLocationTrackingEnabledUseCase @Inject constructor(
    private val repository: UserLocationRepository
) {

    operator fun invoke(value: Boolean) {
        repository.updateTrackingEnabled(value)
    }
}