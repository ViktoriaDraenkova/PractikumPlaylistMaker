package com.practicum.appplaylistmaker.domain.audioplayer

import com.practicum.appplaylistmaker.domain.models.Track

interface AudioplayerInteractor {

    fun playTrack()
    fun pauseTrack()

    fun getTime(): String

    fun prepareTrack(
        track: Track,
        onPreparedListener: () -> Unit,
        onCompletionListener: () -> Unit
    )
}