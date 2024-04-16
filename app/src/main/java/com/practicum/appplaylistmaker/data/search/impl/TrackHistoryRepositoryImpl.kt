package com.practicum.appplaylistmaker.data.search.impl

import android.content.Context
import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import com.google.gson.Gson
import com.practicum.appplaylistmaker.HISTORY_TRACKS_KEY
import com.practicum.appplaylistmaker.PRACTICUM_EXAMPLE_PREFERENCES
import com.practicum.appplaylistmaker.data.search.TrackHistoryRepository
import com.practicum.appplaylistmaker.domain.models.Track

class TrackHistoryRepositoryImpl( val sharedPreferences: SharedPreferences) : TrackHistoryRepository {
    companion object{
       private const val MAX_HISTORY_SIZE =10
    }

    override fun getTracks(): List<Track> {
        val tracksHistory = sharedPreferences.getString(HISTORY_TRACKS_KEY, null)
        if (tracksHistory != null) {
            return createTrackListFromJson(tracksHistory)
        }
        return arrayListOf()
    }

    private fun createTrackListFromJson(json: String): List<Track> =
         Gson().fromJson(json, Array<Track>::class.java).toList()

    override fun pushTrack(track: Track) {
        var history = getTracks()
        history = history.filter { currentTrack -> currentTrack != track }
        history.toMutableList().add(0, track)
        history = ArrayList(history.take(MAX_HISTORY_SIZE) )
        sharedPreferences.edit().putString(HISTORY_TRACKS_KEY, Gson().toJson(history)).apply()
    }

    override fun clearTracks() {
        sharedPreferences.edit().putString(HISTORY_TRACKS_KEY, Gson().toJson(arrayOf<Track>()))
            .apply()
    }
}