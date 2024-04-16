package com.practicum.appplaylistmaker.domain.search.impl

import android.util.Log
import com.practicum.appplaylistmaker.data.search.TrackHistoryRepository
import com.practicum.appplaylistmaker.data.search.TrackRepository
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.search.SearchInteractor

class SearchInteractorImpl(var trackHistoryRepository: TrackHistoryRepository, var trackRepository: TrackRepository) : SearchInteractor {
    override fun searchTrack(
        trackName: String,
        onSuccess: (tracks: List<Track>) -> Unit,
        onFailure: () -> Unit
    ) {
        trackRepository.search(trackName, onSuccess, onFailure)


    }

    override fun clearTrackHistory() {
        trackHistoryRepository.clearTracks()
    }

    override fun getTracksHistory(): List<Track> {
        return trackHistoryRepository.getTracks()
    }

    override fun addTrackToHistory(track: Track) {
        trackHistoryRepository.pushTrack(track)
    }
}