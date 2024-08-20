package com.practicum.appplaylistmaker.domain.playlist.impl

import android.util.Log
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.playlist.PlaylistInteractor
import com.practicum.appplaylistmaker.domain.playlist.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(val playlistRepository: PlaylistRepository):PlaylistInteractor {
    override suspend fun addPlaylist(playlist: Playlist) {
        playlistRepository.addPlaylist(playlist)
    }
    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long){
        Log.d("interactor", "Inserting track $track to playlist $playlistId")
        playlistRepository.insertTrackToPlaylist(track, playlistId)
    }

    override fun getPlaylist(): Flow<List<Playlist>> {
        return playlistRepository.getPlaylists()
    }
}