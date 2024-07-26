package com.practicum.appplaylistmaker.data.db

import com.practicum.appplaylistmaker.data.dto.TrackDto
import com.practicum.appplaylistmaker.domain.models.Track

class TrackConvertor {

    fun mapTrackToTrackDto(track: Track): TrackDto {
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
            track.previewUrl
        )
    }
    fun map(track: TrackDto): TrackEntity {
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
            track.previewUrl
        )
    }

    fun map(track: TrackEntity): Track {
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
            track.previewUrl
        )
    }
}