package com.practicum.appplaylistmaker.data.search.impl

import com.google.gson.Gson
import com.practicum.appplaylistmaker.api.ITunesApi
import com.practicum.appplaylistmaker.data.api.SearchTrackResponse
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.data.search.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


class TrackRepositoryImpl(val iTunesService: ITunesApi) : TrackRepository {

    override fun getCurrentTrack(trackJson: String?): Track? {
        return Gson().fromJson(trackJson, Track::class.java)
    }

    override suspend fun search(
        trackName: String,
        ): List<Track>?{

        if (trackName.isEmpty()) {
            return null
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = iTunesService.search(trackName)
                response.results
            } catch (e: Throwable) {
                null
            }
        }
    }
}