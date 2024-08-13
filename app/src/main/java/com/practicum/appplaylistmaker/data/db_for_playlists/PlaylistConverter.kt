package com.practicum.appplaylistmaker.data.db_for_playlists

import com.practicum.appplaylistmaker.data.db.TrackConvertor
import com.practicum.appplaylistmaker.data.db_for_playlists.dao.JoinPlaylistTrackEntity
import com.practicum.appplaylistmaker.data.db_for_playlists.dao.PlaylistEntity
import com.practicum.appplaylistmaker.data.dto.PlaylistDto
import com.practicum.appplaylistmaker.domain.models.Playlist

class PlaylistConverter {
    val trackConverter: TrackConvertor = TrackConvertor()

    fun mapJoinPlaylistTrackEntityToPlaylist(entity: JoinPlaylistTrackEntity): Playlist {
        return Playlist(
            playlistId = entity.playlist.playlistId,
            playlistName = entity.playlist.playlistName,
            description = entity.playlist.description,
            imagePath = entity.playlist.imagePath,
            tracksCountInPlaylist = entity.tracks.size,
            tracks = entity.tracks.map { trackEntity ->
                trackConverter.mapTrackEntityToTrack(
                    trackEntity
                )
            }
        )
    }

    fun mapPlaylistToPlaylistDto(playlist: Playlist): PlaylistDto {
        return PlaylistDto(
            playlistName = playlist.playlistName,
            description = playlist.description,
            imagePath = playlist.imagePath,
            tracksCountInPlaylist = playlist.tracksCountInPlaylist,
        )
    }

    fun mapPlaylistDaoToPlaylistEntity(playlistDto: PlaylistDto): PlaylistEntity {

        return PlaylistEntity(
            playlistName = playlistDto.playlistName,
            description = playlistDto.description,
            imagePath = playlistDto.imagePath,
            tracksCountInPlaylist = playlistDto.tracksCountInPlaylist,
        )
    }

    fun mapPlaylistEntityToPlaylist(playlistEntity: PlaylistEntity): Playlist {

        return Playlist(
            playlistId = playlistEntity.playlistId,
            playlistName = playlistEntity.playlistName,
            description = playlistEntity.description,
            imagePath = playlistEntity.imagePath,
            tracksCountInPlaylist = playlistEntity.tracksCountInPlaylist,
        )
    }
}