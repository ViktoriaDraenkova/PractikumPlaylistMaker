package com.practicum.appplaylistmaker.domain.playlist

import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {
    suspend fun addPlaylist(playlist: Playlist)

    fun getPlaylist(): Flow<List<Playlist>>
    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)

}