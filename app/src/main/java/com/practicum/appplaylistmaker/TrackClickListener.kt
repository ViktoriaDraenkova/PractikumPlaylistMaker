package com.practicum.appplaylistmaker

import com.practicum.appplaylistmaker.domain.models.Track


fun interface TrackClickListener {
    fun onTrackClick(track: Track)
}