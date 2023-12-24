package com.komoot.vchetrari.challenge.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.komoot.vchetrari.challenge.data.model.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM Photo ORDER BY timestampMillis DESC")
    fun getAllLatest(): Flow<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(photo: Photo)

    @Query("DELETE FROM Photo")
    suspend fun deleteAll()
}