package com.komoot.vchetrari.challenge.data

import com.komoot.vchetrari.challenge.data.model.Photo
import com.komoot.vchetrari.challenge.data.model.UserLocation
import com.komoot.vchetrari.challenge.data.source.PhotoByUserLocationDataSource
import com.komoot.vchetrari.challenge.data.source.PhotoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    private val photoByUserLocationDataSource: PhotoByUserLocationDataSource,
    private val photoDao: PhotoDao
) {

    suspend fun getByLocation(userLocation: UserLocation): Photo? = photoByUserLocationDataSource(userLocation)

    suspend fun clearAll() {
        photoDao.deleteAll()
    }

    suspend fun save(photo: Photo) {
        photoDao.save(photo)
    }

    fun getAllByDateDescending(): Flow<List<Photo>> = photoDao.getAllLatest()
}