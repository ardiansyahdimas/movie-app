package com.movies.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.core.data.source.local.entity.VideoMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoMovieDao {

    @Query("SELECT * FROM videos_movie where movie_id=:movieId")
    fun getVideoMoviesByMovieId(movieId: Int): Flow<List<VideoMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideosMovie(videoList: List<VideoMovieEntity>)

}