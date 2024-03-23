package com.practicum.appplaylistmaker.api;

import com.practicum.appplaylistmaker.domain.models.Track

data class SearchTrackResponse(
    val results: ArrayList<Track>
)
