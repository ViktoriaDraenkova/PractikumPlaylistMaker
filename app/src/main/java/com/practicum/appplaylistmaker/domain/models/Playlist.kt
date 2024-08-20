package com.practicum.appplaylistmaker.domain.models

data class Playlist (
    val playlistId: Long = 0,
    val playlistName:String,
    val description: String,
    val imagePath:String,
    val tracksCountInPlaylist: Int,
    val tracks: List<Track> = emptyList(),
)