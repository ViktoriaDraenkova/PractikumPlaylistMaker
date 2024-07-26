package com.practicum.appplaylistmaker.data.mediateka

import com.practicum.appplaylistmaker.data.db.AppDatabase
import com.practicum.appplaylistmaker.data.db.TrackConvertor
import com.practicum.appplaylistmaker.data.db.TrackEntity
import com.practicum.appplaylistmaker.data.dto.TrackDto
import com.practicum.appplaylistmaker.domain.db.FavouritesRepository
import com.practicum.appplaylistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavouritesRepositoryImpl(
    private val database: AppDatabase,
    private val trackConvertor: TrackConvertor,
) : FavouritesRepository {
    override fun getFavouriteTracks(): Flow<List<Track>> = flow {
        val tracks = database.trackDao().getTracks()
        emit(convertFromTrackEntity(tracks).reversed())
    }

    private fun convertFromTrackEntity(tracks: List<TrackEntity>): List<Track> {
        return tracks.map { track -> trackConvertor.map(track) }
    }

    override suspend fun getInfoAboutLikedOrNot(track: Track): Boolean {
        return database.trackDao().getInfoAboutLikedOrNot(track.trackId)>0
    }

    override suspend fun saveFavouriteTrack(track: Track) {
        val trackDto = trackConvertor.mapTrackToTrackDto(track)
        val trackEntity = trackConvertor.map(trackDto)
        database.trackDao().insertTrack(trackEntity)
    }
    override suspend fun deleteTrackFromDB(track: Track){
        val trackDto = trackConvertor.mapTrackToTrackDto(track)
        val trackEntity=trackConvertor.map(trackDto)
        database.trackDao().delete(trackEntity)
    }
}