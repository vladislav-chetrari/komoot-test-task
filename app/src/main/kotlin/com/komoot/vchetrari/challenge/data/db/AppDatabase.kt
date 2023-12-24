package com.komoot.vchetrari.challenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.komoot.vchetrari.challenge.data.model.Photo
import com.komoot.vchetrari.challenge.data.source.PhotoDao

@Database(
    entities = [Photo::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object{
        const val NAME = "Location Challenge DB"
    }
}