package com.practicum.appplaylistmaker.ui.audioplayer.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.appplaylistmaker.domain.audioplayer.AudioplayerInteractor
import com.practicum.appplaylistmaker.domain.models.Track

class AudioplayerViewModel(

    private val audioplayerInteractor: AudioplayerInteractor
) : ViewModel() {

    fun setTrack(track: Track) {
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

    private var playerState = MutableLiveData<AudioplayerState>(AudioplayerState.STATE_DEFAULT)

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