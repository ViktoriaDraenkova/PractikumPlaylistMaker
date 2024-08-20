package com.practicum.appplaylistmaker.ui.media.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.playlist.PlaylistInteractor
import kotlinx.coroutines.launch

class PlaylistsViewModel(val playlistInteractor: PlaylistInteractor) : ViewModel() {


   private val playlistsLiveData = MutableLiveData<List<Playlist>>()
    fun getObservablePlaylists():LiveData<List<Playlist>> = playlistsLiveData


    fun fillData(){
        viewModelScope.launch {
            playlistInteractor.getPlaylist().collect{playlists->
                playlistsLiveData.value = playlists
            }
        }
    }

}