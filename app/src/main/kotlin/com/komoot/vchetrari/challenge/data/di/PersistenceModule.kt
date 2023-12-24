package com.komoot.vchetrari.challenge.data.di

import android.content.Context
import androidx.room.Room
import com.komoot.vchetrari.challenge.data.db.AppDatabase
import com.komoot.vchetrari.challenge.data.source.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    internal fun database(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME)
        .build()

    @Provides
    internal fun photoDao(db: AppDatabase): PhotoDao = db.photoDao()
}