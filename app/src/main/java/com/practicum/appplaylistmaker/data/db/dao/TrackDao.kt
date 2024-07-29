package com.practicum.appplaylistmaker.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.appplaylistmaker.data.db.TrackEntity
import com.practicum.appplaylistmaker.domain.models.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Query("SELECT * FROM track_table ORDER BY favouriteAddedTimestamp ASC")
    suspend fun getTracks(): List<TrackEntity>


    @Query("SELECT COUNT(*) FROM track_table WHERE trackId = :trackId")
    suspend fun getInfoAboutLikedOrNot(trackId: Int):Int

    @Delete
    suspend fun delete(track: TrackEntity)
}