package com.practicum.appplaylistmaker.data.search

import com.practicum.appplaylistmaker.domain.models.Track

interface TrackHistoryRepository {
    fun getTracks(): List<Track>

    fun pushTrack(track: Track)

    fun clearTracks()
}