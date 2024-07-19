package com.practicum.appplaylistmaker.domain.search.impl

import com.practicum.appplaylistmaker.data.search.TrackHistoryRepository
import com.practicum.appplaylistmaker.data.search.TrackRepository
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.search.SearchInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchInteractorImpl(var trackHistoryRepository: TrackHistoryRepository, var trackRepository: TrackRepository) : SearchInteractor {
    override suspend fun searchTrack(
        trackName: String): Flow<List<Track>?> = flow {
        emit(trackRepository.search(trackName) )
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