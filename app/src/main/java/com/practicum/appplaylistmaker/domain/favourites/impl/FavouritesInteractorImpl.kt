package com.practicum.appplaylistmaker.domain.favourites.impl

import com.practicum.appplaylistmaker.domain.favourites.FavouritesInteractor
import com.practicum.appplaylistmaker.domain.favourites.FavouritesRepository
import com.practicum.appplaylistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavouritesInteractorImpl(private val favouritesRepository: FavouritesRepository) :
    FavouritesInteractor {
    override fun favouriteTracks(): Flow<List<Track>> {
        return favouritesRepository.getFavouriteTracks()
    }

    override suspend fun saveFavouriteTrack(track: Track){
        favouritesRepository.saveFavouriteTrack(track)
    }

    override suspend fun getInfoAboutLikedOrNot(track: Track): Boolean {
        return favouritesRepository.getInfoAboutLikedOrNot(track)
    }
    override suspend fun deleteTrackFromDB(track: Track){
        favouritesRepository.deleteTrackFromDB(track)
    }
}