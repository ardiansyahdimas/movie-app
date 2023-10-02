package com.movies.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movies.core.data.source.local.entity.ReviewMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewMovieDao {
    @Query("SELECT * FROM reviews where movie_id=:movieId")
    fun getMoviesByMovieId(movieId:Int): Flow<List<ReviewMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviewsMovie(reviewList: List<ReviewMovieEntity>)
}