package com.practicum.appplaylistmaker.domain.playlist

import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {

    suspend fun addPlaylist(playlist: Playlist)

    fun getPlaylists(): Flow<List<Playlist>>

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteTrack(playlistId: Long, trackId:Long )
    suspend fun redactPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlistId: Long)

}