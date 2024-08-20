package com.practicum.appplaylistmaker.domain.models

data class Playlist (
    val playlistId: Long = 0,
    var playlistName:String,
    var description: String,
    var imagePath:String,
    val tracksCountInPlaylist: Int,
    val tracks: List<Track> = emptyList(),
)