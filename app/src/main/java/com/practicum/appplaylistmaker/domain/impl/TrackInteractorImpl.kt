package com.practicum.appplaylistmaker.domain.impl

import com.practicum.appplaylistmaker.domain.api.TrackInteractor
import com.practicum.appplaylistmaker.domain.api.TrackRepository
import com.practicum.appplaylistmaker.domain.models.Track

class TrackInteractorImpl(private val trackRepository: TrackRepository): TrackInteractor {
    override fun getCurrentTrack(): Track? {
        return trackRepository.getCurrentTrack()
    }
}