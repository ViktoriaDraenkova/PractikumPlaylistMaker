package com.practicum.appplaylistmaker.data.db_for_playlists.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PlaylistDao {

    @Insert
    suspend fun insertPlaylist(playlistEntity: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity): Long

    @Transaction
    suspend fun insertTrackToPlaylist(track: TrackEntity, playlistId: Long) {
        val trackId = insertTrack(track)
        Log.d("insertTrackToPlaylist", "Inserted $trackId")
        insertPlaylistTrackCrossRef(PlaylistTrackCrossRef(playlistId, trackId))
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistTrackCrossRef(crossRef: PlaylistTrackCrossRef)

    @Query("SELECT * FROM playlist_table ORDER BY playlistId DESC")
    suspend fun getPlaylists():List<PlaylistEntity>

    @Transaction
    @Query("SELECT * FROM playlist_table ORDER BY playlistId DESC")
    suspend fun getPlaylistsWithTracks(): List<JoinPlaylistTrackEntity>
}