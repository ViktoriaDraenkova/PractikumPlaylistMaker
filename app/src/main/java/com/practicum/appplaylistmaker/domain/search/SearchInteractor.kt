package com.practicum.appplaylistmaker.domain.search

import com.practicum.appplaylistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {

    suspend fun searchTrack(trackName: String): Flow<List<Track>?>

    fun clearTrackHistory()
    fun getTracksHistory(): List<Track>
    fun addTrackToHistory(track: Track)
}