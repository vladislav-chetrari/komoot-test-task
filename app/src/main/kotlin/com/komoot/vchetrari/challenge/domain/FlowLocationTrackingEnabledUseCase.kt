package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.UserLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlowLocationTrackingEnabledUseCase @Inject constructor(
    private val repository: UserLocationRepository
){

    operator fun invoke(): Flow<Boolean> = repository.isTrackingEnabled()
}