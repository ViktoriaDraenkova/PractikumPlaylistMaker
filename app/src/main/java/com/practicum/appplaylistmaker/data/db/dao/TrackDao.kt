package com.practicum.appplaylistmaker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.appplaylistmaker.data.db.TrackEntityFavourite


@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntityFavourite)

    @Query("SELECT * FROM track_table ORDER BY favouriteAddedTimestamp ASC")
    suspend fun getTracks(): List<TrackEntityFavourite>


    @Query("SELECT COUNT(*) FROM track_table WHERE trackId = :trackId")
    suspend fun getInfoAboutLikedOrNot(trackId: Long):Int

    @Delete
    suspend fun delete(track: TrackEntityFavourite)
}