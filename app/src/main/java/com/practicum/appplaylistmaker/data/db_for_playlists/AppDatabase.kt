package com.practicum.appplaylistmaker.data.db_for_playlists

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.appplaylistmaker.data.db_for_playlists.dao.PlaylistDao
import com.practicum.appplaylistmaker.data.db_for_playlists.dao.PlaylistEntity
import com.practicum.appplaylistmaker.data.db_for_playlists.dao.PlaylistTrackCrossRef
import com.practicum.appplaylistmaker.data.db_for_playlists.dao.TrackEntity


@Database(version = 1, entities = [PlaylistEntity:: class, TrackEntity::class, PlaylistTrackCrossRef::class])
abstract class AppDatabase: RoomDatabase() {
    abstract fun PlaylistDao(): PlaylistDao
}