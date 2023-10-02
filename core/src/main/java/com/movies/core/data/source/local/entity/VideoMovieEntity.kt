package com.movies.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "videos_movie", indices = [androidx.room.Index(value = ["id"], unique = true)])
data class VideoMovieEntity(
    @PrimaryKey
    @Nonnull
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "movie_id")
    var movieId: Int,

    @ColumnInfo(name = "official")
    var official: Boolean?,

    @ColumnInfo(name = "site")
    var site: String?,

    @ColumnInfo(name = "size")
    var size: Int?,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "type")
    var type: String?,

    @ColumnInfo(name = "key")
    var key: String?,
)