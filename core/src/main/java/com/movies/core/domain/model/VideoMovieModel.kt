package com.movies.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoMovieModel(
    val id: String,
    val movieId: Int,
    val official: Boolean?,
    val site: String?,
    val size: Int?,
    val name: String?,
    val type: String?,
    val key: String?,
) : Parcelable