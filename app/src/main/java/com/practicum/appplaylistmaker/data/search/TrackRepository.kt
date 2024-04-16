package com.practicum.appplaylistmaker.data.search

import com.practicum.appplaylistmaker.domain.models.Track

interface TrackRepository {
    fun getCurrentTrack(trackJson: String?): Track?
    fun search(trackName: String,
               onSuccess: (tracks: ArrayList<Track>) -> Unit,
               onFailure: () -> Unit)
}