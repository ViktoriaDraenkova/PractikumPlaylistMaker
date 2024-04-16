package com.practicum.appplaylistmaker.domain.search.impl

import com.practicum.appplaylistmaker.domain.search.TrackInteractor
import com.practicum.appplaylistmaker.data.search.TrackRepository
import com.practicum.appplaylistmaker.domain.models.Track

class TrackInteractorImpl(private val trackRepository: TrackRepository): TrackInteractor {
    override fun getCurrentTrack(trackJson: String?): Track? {
        return trackRepository.getCurrentTrack(trackJson)
    }
}