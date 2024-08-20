package com.practicum.appplaylistmaker.ui.media

import com.practicum.appplaylistmaker.domain.models.Playlist

fun interface PlaylistClickListener {
    fun onPlaylistClick(playlist: Playlist)
}