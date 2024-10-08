package com.practicum.appplaylistmaker.domain.favourites

import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getFavouriteTracks(): Flow<List<Track>>
    suspend fun saveFavouriteTrack(track: Track)
    suspend fun getInfoAboutLikedOrNot(track: Track): Boolean
    suspend fun deleteTrackFromDB(track: Track)

}