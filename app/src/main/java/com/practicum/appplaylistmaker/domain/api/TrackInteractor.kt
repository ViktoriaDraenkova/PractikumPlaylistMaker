package com.practicum.appplaylistmaker.domain.api

import com.practicum.appplaylistmaker.domain.models.Track

interface TrackInteractor {
    fun getCurrentTrack(trackJson:String?): Track?
}