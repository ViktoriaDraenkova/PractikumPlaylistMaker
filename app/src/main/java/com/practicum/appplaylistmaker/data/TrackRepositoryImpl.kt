package com.practicum.appplaylistmaker.data

import android.content.Intent
import com.google.gson.Gson
import com.practicum.appplaylistmaker.KEY_FOR_TRACK
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.api.TrackRepository

class TrackRepositoryImpl(private var intent: Intent): TrackRepository {

    override fun getCurrentTrack(): Track? {
        val arguments = intent.extras
        val trackJson: String = arguments?.getString(KEY_FOR_TRACK) ?: return null

        return Gson().fromJson(trackJson, Track::class.java)
    }
}