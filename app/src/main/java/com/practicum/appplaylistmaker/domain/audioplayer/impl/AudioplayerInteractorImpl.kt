package com.practicum.appplaylistmaker.domain.audioplayer.impl

import com.practicum.appplaylistmaker.data.audioplayer.Audioplayer
import com.practicum.appplaylistmaker.domain.audioplayer.AudioplayerInteractor
import com.practicum.appplaylistmaker.domain.models.Track

class AudioplayerInteractorImpl(private var audioplayer: Audioplayer) : AudioplayerInteractor {
    override fun playTrack() {
        audioplayer.playMusic()
    }

    override fun pauseTrack() {
        audioplayer.stopmusic()
    }

    override fun getTime(): String {
        return audioplayer.getTime()
    }

    override fun prepareTrack(
        track: Track,
        onPreparedListener: () -> Unit,
        onCompletionListener: () -> Unit
    ) {
        audioplayer.prepareTrack(track, onPreparedListener, onCompletionListener)
    }


}