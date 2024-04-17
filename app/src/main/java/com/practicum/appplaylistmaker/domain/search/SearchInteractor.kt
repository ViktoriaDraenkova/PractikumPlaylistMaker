package com.practicum.appplaylistmaker.domain.search

import com.practicum.appplaylistmaker.domain.models.Track

interface SearchInteractor {

    fun searchTrack(trackName: String,
                    onSuccess: (tracks: List<Track>) -> Unit,
                    onFailure: () -> Unit)

    fun clearTrackHistory()
    fun getTracksHistory(): List<Track>
    fun addTrackToHistory(track: Track)
}