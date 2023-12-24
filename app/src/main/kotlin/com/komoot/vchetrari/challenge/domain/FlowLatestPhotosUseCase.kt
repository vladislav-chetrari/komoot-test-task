package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.PhotoRepository
import com.komoot.vchetrari.challenge.data.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlowLatestPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {

    operator fun invoke(): Flow<List<Photo>> = photoRepository.getAllByDateDescending()
}