package com.practicum.appplaylistmaker.ui.media

import com.practicum.appplaylistmaker.domain.models.Track

sealed interface FavouritesTrackState{

    data class Content(
        val tracks:List<Track>
    ):FavouritesTrackState

    object Empty: FavouritesTrackState
}