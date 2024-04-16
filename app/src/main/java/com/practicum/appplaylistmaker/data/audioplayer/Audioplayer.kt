package com.practicum.appplaylistmaker.data.audioplayer

import com.practicum.appplaylistmaker.domain.models.Track

interface Audioplayer {
    fun playMusic()
    fun stopmusic()
    fun prepareTrack(
        track: Track,
        onPreparedListener: () -> Unit,
        onCompletionListener: () -> Unit
    )

    fun getTime(): String
}