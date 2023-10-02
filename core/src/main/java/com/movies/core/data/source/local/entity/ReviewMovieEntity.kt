package com.movies.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "reviews", indices = [androidx.room.Index(value = ["id"], unique = true)])
data class ReviewMovieEntity(
    @PrimaryKey
    @Nonnull
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "movie_id")
    var movieId: Int,

    @ColumnInfo(name = "rating")
    var rating: Double?,

    @ColumnInfo(name = "avatar_path")
    var avatarPath: String?,

    @ColumnInfo(name = "author")
    var author: String?,

    @ColumnInfo(name = "content")
    var content: String?,

    @ColumnInfo(name = "created_at")
    var created_at: String?,
)
