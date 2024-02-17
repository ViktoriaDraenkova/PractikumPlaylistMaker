package com.practicum.appplaylistmaker.domain.api

import com.practicum.appplaylistmaker.domain.models.Track

interface TrackRepository {
    fun getCurrentTrack(): Track?
}