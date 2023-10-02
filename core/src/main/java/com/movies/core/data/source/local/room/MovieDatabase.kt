package com.movies.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movies.core.data.source.local.entity.MovieEntity
import com.movies.core.data.source.local.entity.ReviewMovieEntity
import com.movies.core.data.source.local.entity.VideoMovieEntity

@Database(entities = [
    MovieEntity::class,
    ReviewMovieEntity::class,
    VideoMovieEntity::class
                     ], version = 2, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun reviewMovieDao(): ReviewMovieDao
    abstract fun videoMovieDao(): VideoMovieDao
}