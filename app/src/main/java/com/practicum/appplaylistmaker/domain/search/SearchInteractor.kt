package com.practicum.appplaylistmaker.domain.search

import com.practicum.appplaylistmaker.domain.models.Track

interface SearchInteractor {

    fun searchTrack(trackName: String,
                    onSuccess: (tracks: ArrayList<Track>) -> Unit,
                    onFailure: () -> Unit)

    fun clearTrackHistory()
    fun getTracksHistory(): ArrayList<Track>
    fun addTrackToHistory(track: Track)
}