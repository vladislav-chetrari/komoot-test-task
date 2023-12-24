package com.komoot.vchetrari.challenge.data.model

data class UserLocation(
    val latitude: Double,
    val longitude: Double,
    val distanceFromLastLocationMeters: Int
)