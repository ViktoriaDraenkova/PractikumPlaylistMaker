package com.practicum.appplaylistmaker.data.dto

class TrackDto(
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: Int,
    val artworkUrl100: String,
    val releaseDate: String,
    val collectionName: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl:String,
    val playlistId: Long = -1,
)