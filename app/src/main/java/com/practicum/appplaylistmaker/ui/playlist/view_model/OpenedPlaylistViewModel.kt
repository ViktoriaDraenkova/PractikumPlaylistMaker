package com.practicum.appplaylistmaker.ui.playlist.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.playlist.PlaylistInteractor
import kotlinx.coroutines.launch

class OpenedPlaylistViewModel(val playlistInteractor: PlaylistInteractor) : ViewModel() {

    private var tracksLiveData = MutableLiveData<List<Track>>()

    fun getTracksLiveData(): LiveData<List<Track>> = tracksLiveData

    fun initTracks(tracks: List<Track>) {
        tracksLiveData.value = tracks
    }
    fun deletePlaylist(playlistId: Long) {
        viewModelScope.launch {
            playlistInteractor.deletePlaylist(playlistId)
        }
    }

    fun redactPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            playlistInteractor.redactPlaylist(playlist)
        }
    }

    fun deleteTrack(playlistId: Long, trackId: Long) {
        viewModelScope.launch {
            playlistInteractor.deleteTrack(playlistId, trackId)
        }
        tracksLiveData.value = tracksLiveData.value?.filter { it.trackId != trackId }
    }

}