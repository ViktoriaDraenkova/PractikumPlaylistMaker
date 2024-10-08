package com.practicum.appplaylistmaker.data.db

import com.practicum.appplaylistmaker.data.db_for_playlists.dao.TrackEntity
import com.practicum.appplaylistmaker.data.dto.TrackDto
import com.practicum.appplaylistmaker.domain.models.Track
import java.util.Calendar

class TrackConvertor {

    fun mapTrackToTrackDto(track: Track, playlistId: Long = -1): TrackDto {
        return TrackDto(
            track.trackId,
            track.trackName,
            track.artistName,
            track.trackTime,
            track.artworkUrl100,
            track.releaseDate,
            track.collectionName,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            playlistId,
        )
    }
    fun mapTrackDtoToTrackEntityFavourite(track: TrackDto): TrackEntityFavourite {
        return TrackEntityFavourite(
            track.trackId,
            track.trackName,
            track.artistName,
            track.trackTime,
            track.artworkUrl100,
            track.releaseDate,
            track.collectionName,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            Calendar.getInstance().timeInMillis

        )
    }

    fun mapTrackDtoToTrackEntityForPlaylist(track: TrackDto): TrackEntity {
        return TrackEntity(
            track.trackId,
            track.trackName,
            track.artistName,
            track.trackTime,
            track.artworkUrl100,
            track.releaseDate,
            track.collectionName,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
        )
    }

    fun mapTrackEntityFavouriteToTrack(track: TrackEntityFavourite): Track {
        return Track(
            track.trackId,
            track.trackName,
            track.artistName,
            track.trackTime,
            track.artworkUrl100,
            track.releaseDate,
            track.collectionName,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
        )
    }

    fun mapTrackEntityToTrack(track: TrackEntity): Track {
        return Track(
            track.trackId,
            track.trackName,
            track.artistName,
            track.trackTime,
            track.artworkUrl100,
            track.releaseDate,
            track.collectionName,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
        )
    }
}