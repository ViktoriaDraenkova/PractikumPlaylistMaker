package com.practicum.appplaylistmaker.ui.new_playlist.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.playlist.PlaylistInteractor
import kotlinx.coroutines.launch

class NewPlaylistViewModel(val playlistInteractor: PlaylistInteractor) : ViewModel() {

    fun addPlaylist(playlist: Playlist){
        viewModelScope.launch {
            playlistInteractor.addPlaylist(playlist)
        }
    }

    fun editPlaylist(playlist: Playlist){
        viewModelScope.launch {
            playlistInteractor.redactPlaylist(playlist)
        }
    }

}