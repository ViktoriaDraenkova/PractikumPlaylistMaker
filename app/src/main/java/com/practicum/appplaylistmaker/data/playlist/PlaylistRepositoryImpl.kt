package com.practicum.appplaylistmaker.data.playlist

import android.util.Log
import com.practicum.appplaylistmaker.data.db.TrackConvertor
import com.practicum.appplaylistmaker.data.db_for_playlists.AppDatabase
import com.practicum.appplaylistmaker.data.db_for_playlists.PlaylistConverter
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.domain.playlist.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(
    val database: AppDatabase,
    val playlistConverter: PlaylistConverter,
    val trackConvertor: TrackConvertor
) : PlaylistRepository {
    override suspend fun addPlaylist(playlist: Playlist) {
        val playlistDto = playlistConverter.mapPlaylistToPlaylistDto(playlist)
        val playlistEntity = playlistConverter.mapPlaylistDaoToPlaylistEntity(playlistDto)
        database.PlaylistDao().insertPlaylist(playlistEntity)
    }

    override fun getPlaylists(): Flow<List<Playlist>> = flow {
        emit(database.PlaylistDao().getPlaylistsWithTracks().map { playlistEntity ->
            playlistConverter.mapJoinPlaylistTrackEntityToPlaylist(playlistEntity)
        })
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        Log.d("repository", "Inserting track $track")
        database.PlaylistDao().insertTrackToPlaylist(
            trackConvertor.mapTrackDtoToTrackEntityForPlaylist(
                trackConvertor.mapTrackToTrackDto(track),
            ),
            playlistId
        )
    }

    override suspend fun deleteTrack(playlistId: Long, trackId: Long) {
        database.PlaylistDao().deleteTrackFromPlaylist(playlistId, trackId)
    }

    override suspend fun redactPlaylist(playlist: Playlist) {
        val playlistEntity = playlistConverter.mapPlaylistToPlaylistEntity(playlist)
        Log.d("UPDATE playlist. entity", playlistEntity.toString())
        database.PlaylistDao().updatePlaylist(playlistEntity)
    }

    override suspend fun deletePlaylist(playlistId: Long) {
        database.PlaylistDao().deletePlaylist(playlistId)
    }

}