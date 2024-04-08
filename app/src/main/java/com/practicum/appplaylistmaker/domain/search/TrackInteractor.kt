package com.practicum.appplaylistmaker.domain.search

import com.practicum.appplaylistmaker.domain.models.Track

interface TrackInteractor {
    fun getCurrentTrack(trackJson:String?): Track?
}