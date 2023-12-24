package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.PhotoRepository
import javax.inject.Inject

class ClearPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke() {
        repository.clearAll()
    }
}