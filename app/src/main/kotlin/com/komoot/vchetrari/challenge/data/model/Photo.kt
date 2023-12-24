package com.komoot.vchetrari.challenge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    @PrimaryKey val id: String,
    @ColumnInfo val timestampMillis: Long,
    @ColumnInfo val url: String
)