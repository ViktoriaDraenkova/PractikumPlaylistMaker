package com.practicum.appplaylistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.appplaylistmaker.data.db.dao.TrackDao

@Database(version = 1, entities = [TrackEntityFavourite::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}