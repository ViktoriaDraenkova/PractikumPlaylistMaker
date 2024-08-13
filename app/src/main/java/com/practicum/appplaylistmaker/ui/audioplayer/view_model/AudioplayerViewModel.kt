package com.practicum.appplaylistmaker.ui.audioplayer.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.appplaylistmaker.domain.audioplayer.AudioplayerInteractor
import com.practicum.appplaylistmaker.domain.favourites.FavouritesInteractor
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.playlist.PlaylistInteractor
import kotlinx.coroutines.launch

class AudioplayerViewModel(
    private val audioplayerInteractor: AudioplayerInteractor,
    private val favouritesInteractor: FavouritesInteractor,
    private val playlistInteractor: PlaylistInteractor,
) : ViewModel() {

    fun setTrack(track: Track) {
        pausePlayer()
        playerState.value = AudioplayerState.STATE_DEFAULT
        viewModelScope.launch {
            if (favouritesInteractor.getInfoAboutLikedOrNot(track)) {
                trackLikeState.value = LikeState.STATE_LIKED
            } else trackLikeState.value = LikeState.STATE_DISLIKED

        }

        audioplayerInteractor.prepareTrack(
            track,
            {
                playerState.value = AudioplayerState.STATE_PREPARED
            },
            {
                playerState.value = AudioplayerState.STATE_PREPARED
            }
        )
    }

    enum class AudioplayerState(s: String) {
        STATE_DEFAULT("default"),
        STATE_PREPARED("prepared"),
        STATE_PLAYING("playing"),
        STATE_PAUSED("paused")
    }

    enum class LikeState(s: String) {
        STATE_LIKED("liked"),
        STATE_DISLIKED("disliked")
    }

    private var playerState = MutableLiveData(AudioplayerState.STATE_DEFAULT)
    private var trackLikeState = MutableLiveData(LikeState.STATE_DISLIKED)


    fun getPlayerState(): LiveData<AudioplayerState> {
        return playerState
    }

    fun getTrackLikedState(): LiveData<LikeState> {
        return trackLikeState
    }

    fun startPlayer() {
        audioplayerInteractor.playTrack()
        playerState.value = AudioplayerState.STATE_PLAYING
    }

    fun pausePlayer() {
        audioplayerInteractor.pauseTrack()
        playerState.value = AudioplayerState.STATE_PAUSED
    }

    fun getTimeState(): String {
        return audioplayerInteractor.getTime()
    }

    fun likeTrack(track: Track) {
        viewModelScope.launch {
            favouritesInteractor.saveFavouriteTrack(track)
            trackLikeState.value = LikeState.STATE_LIKED

        }
    }

    fun fillPlaylistData() {
        viewModelScope.launch {
            playlistInteractor.getPlaylist().collect { playlists ->
                playlistsLiveData.value = playlists
            }
        }
    }

    fun dislikeTrack(track: Track) {
        viewModelScope.launch {
            favouritesInteractor.deleteTrackFromDB(track)
            trackLikeState.value = LikeState.STATE_DISLIKED
        }
    }

    private val playlistsLiveData = MutableLiveData<List<Playlist>>()
    fun getObservablePlaylists(): LiveData<List<Playlist>> = playlistsLiveData


    fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        Log.d("viewModel", "Inserting track $track to playlist $playlistId")
        viewModelScope.launch {
            playlistInteractor.insertTrackToPlaylist(track, playlistId)
        }
    }
}