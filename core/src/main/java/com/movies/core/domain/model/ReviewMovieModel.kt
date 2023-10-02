package com.movies.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewMovieModel(
    val id: String,
    val movieId: Int,
    val rating: Double?,
    val avatarPath: String?,
    val author: String?,
    val content: String?,
    val created_at: String?,
) : Parcelable
