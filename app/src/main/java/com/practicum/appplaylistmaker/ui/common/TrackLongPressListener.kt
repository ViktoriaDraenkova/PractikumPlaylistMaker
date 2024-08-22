package com.practicum.appplaylistmaker.ui.common

import com.practicum.appplaylistmaker.domain.models.Track

fun interface TrackLongPressListener {
    fun onTrackLongPress(track: Track)
}