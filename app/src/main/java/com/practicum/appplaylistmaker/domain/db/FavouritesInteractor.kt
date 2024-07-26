package com.practicum.appplaylistmaker.domain.db

import com.practicum.appplaylistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavouritesInteractor {
    fun favouriteTracks(): Flow<List<Track>>

    suspend fun saveFavouriteTrack(track: Track)
    suspend fun getInfoAboutLikedOrNot(track: Track): Boolean

    suspend fun deleteTrackFromDB(track: Track)
}