package com.komoot.vchetrari.challenge.presentation.model

import com.komoot.vchetrari.challenge.data.model.Photo

data class MainViewState(
    val isLocationTrackingEnabled: Boolean = false,
    val photos: List<Photo> = emptyList()
)