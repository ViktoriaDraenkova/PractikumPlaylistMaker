package com.practicum.appplaylistmaker.data.db_for_playlists.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

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

    @Transaction
    suspend fun deletePlaylist(playlistId: Long) {
        deletePlaylistTracksCrossref(playlistId)
        deleteCleanPlaylist(playlistId )
    }

    @Query("DELETE FROM PLAYLIST_TRACK_CROSS_REF WHERE playlistId = :playlistId")
    suspend fun deletePlaylistTracksCrossref(playlistId: Long)

    @Query("DELETE FROM PLAYLIST_TABLE WHERE playlistId = :playlistId")
    suspend fun deleteCleanPlaylist(playlistId: Long)

    @Query("DELETE FROM PLAYLIST_TRACK_CROSS_REF WHERE trackId =:trackId AND playlistId=:playlistId")
    suspend fun deleteTrackFromPlaylist(playlistId: Long, trackId: Long)

    @Update
    suspend fun updatePlaylist(playlistEntity: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistTrackCrossRef(crossRef: PlaylistTrackCrossRef)

    @Query("SELECT * FROM playlist_table ORDER BY playlistId DESC")
    suspend fun getPlaylists(): List<PlaylistEntity>

    @Transaction
    @Query("SELECT * FROM playlist_table ORDER BY playlistId DESC")
    suspend fun getPlaylistsWithTracks(): List<JoinPlaylistTrackEntity>
}