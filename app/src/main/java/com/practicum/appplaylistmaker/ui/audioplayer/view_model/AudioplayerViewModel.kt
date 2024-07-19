package com.practicum.appplaylistmaker.ui.audioplayer.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.appplaylistmaker.domain.audioplayer.AudioplayerInteractor
import com.practicum.appplaylistmaker.domain.models.Track
import kotlinx.coroutines.launch

class AudioplayerViewModel(

    private val audioplayerInteractor: AudioplayerInteractor
) : ViewModel() {

    fun setTrack(track: Track) {
        pausePlayer()
        playerState.value = AudioplayerState.STATE_DEFAULT
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

    private var playerState = MutableLiveData(AudioplayerState.STATE_DEFAULT)

    fun getPlayerState(): LiveData<AudioplayerState> {
        return playerState
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
}