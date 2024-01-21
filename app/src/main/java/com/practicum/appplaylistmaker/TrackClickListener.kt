package com.practicum.appplaylistmaker

import com.practicum.appplaylistmaker.api.Track

fun interface TrackClickListener {
    fun onTrackClick(track: Track)
}