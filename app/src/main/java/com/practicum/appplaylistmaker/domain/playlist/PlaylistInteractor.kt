package com.practicum.appplaylistmaker.domain.playlist

import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {
    suspend fun addPlaylist(playlist: Playlist)

    fun getPlaylist(): Flow<List<Playlist>>
    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteTrack(playlistId: Long, trackId: Long)
    suspend fun deletePlaylist(playlist: Long)

    suspend fun redactPlaylist(playlist: Playlist)
}