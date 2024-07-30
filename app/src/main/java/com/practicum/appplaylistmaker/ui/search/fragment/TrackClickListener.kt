package com.practicum.appplaylistmaker.ui.search.fragment

import com.practicum.appplaylistmaker.domain.models.Track


fun interface TrackClickListener {
    fun onTrackClick(track: Track)
}