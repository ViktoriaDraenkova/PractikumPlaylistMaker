package com.practicum.appplaylistmaker.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "track_table")
data class TrackEntityFavourite(
    @PrimaryKey
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: Int,
    val artworkUrl100: String,
    val releaseDate: String,
    val collectionName: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
    val favouriteAddedTimestamp: Long,
)