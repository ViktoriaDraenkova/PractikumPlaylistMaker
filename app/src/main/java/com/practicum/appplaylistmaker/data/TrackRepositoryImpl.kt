package com.practicum.appplaylistmaker.data

import android.content.Intent
import com.google.gson.Gson
import com.practicum.appplaylistmaker.KEY_FOR_TRACK
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.api.TrackRepository

class TrackRepositoryImpl() : TrackRepository {

    override fun getCurrentTrack(trackJson: String?): Track? {

        return Gson().fromJson(trackJson, Track::class.java)
    }

}