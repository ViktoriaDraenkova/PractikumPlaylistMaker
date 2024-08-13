package com.practicum.appplaylistmaker.data.dto

import com.practicum.appplaylistmaker.domain.models.Track

class PlaylistDto(
    val playlistName:String,
    val description: String,
    val imagePath:String,
    val tracksCountInPlaylist: Int,
)