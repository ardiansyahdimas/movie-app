package com.movies.core.domain.usecase

import com.movies.core.data.Resource
import com.movies.core.domain.model.MovieModel
import com.movies.core.domain.model.ReviewMovieModel
import com.movies.core.domain.model.VideoMovieModel
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovies(type:String, page:Int): Flow<Resource<List<MovieModel>>>
    fun getReviewsMovieByMovieId(movieId:Int, page: Int): Flow<Resource<List<ReviewMovieModel>>>
    fun getVideosMovieByMovieId(movieId:Int): Flow<Resource<List<VideoMovieModel>>>
}