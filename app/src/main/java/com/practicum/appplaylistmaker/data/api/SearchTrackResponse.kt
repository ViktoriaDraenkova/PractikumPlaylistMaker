package com.practicum.appplaylistmaker.data.api;

import com.practicum.appplaylistmaker.domain.models.Track

data class SearchTrackResponse(
    val results: List<Track>
)
