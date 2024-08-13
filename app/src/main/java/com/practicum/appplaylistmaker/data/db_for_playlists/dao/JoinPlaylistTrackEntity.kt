package com.practicum.appplaylistmaker.data.db_for_playlists.dao

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.practicum.appplaylistmaker.domain.models.Playlist
import com.practicum.appplaylistmaker.domain.models.Track

data class JoinPlaylistTrackEntity(
    @Embedded
    val playlist: PlaylistEntity,

    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistTrackCrossRef::class)
    )
    val tracks: List<TrackEntity>
)

@Entity(tableName = "playlist_track_cross_ref", primaryKeys = ["playlistId", "trackId"])
data class PlaylistTrackCrossRef(
    val playlistId: Long,
    val trackId: Long,
)

@Entity(tableName = "track_for_playlist_table")
data class TrackEntity(
    @PrimaryKey
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: Int,
    val artworkUrl100: String,
    val releaseDate: String,
    val collectionName: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
//    val parentPlaylistId: Long
)

@Entity(tableName = "playlist_table")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val playlistId: Long = 0,
    val playlistName:String,
    val description: String,
    val imagePath:String,
    val tracksCountInPlaylist: Int,
)
