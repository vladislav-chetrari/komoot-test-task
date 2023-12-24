package com.komoot.vchetrari.challenge.data.source

import com.komoot.vchetrari.challenge.data.model.response.PhotoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoSearchClient {

    @GET("?page=1&per_page=1&radius_units=km")
    suspend fun getByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("radius") radiusKm: Double
    ): PhotoSearchResponse
}