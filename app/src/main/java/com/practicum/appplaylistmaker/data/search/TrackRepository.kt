package com.practicum.appplaylistmaker.data.search

import com.practicum.appplaylistmaker.data.api.SearchTrackResponse
import com.practicum.appplaylistmaker.domain.models.Track

interface TrackRepository {
    fun getCurrentTrack(trackJson: String?): Track?
    suspend fun search(trackName: String): List<Track>?
}