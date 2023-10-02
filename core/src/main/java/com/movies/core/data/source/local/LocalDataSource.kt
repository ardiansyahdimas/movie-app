package com.movies.core.data.source.local

import com.movies.core.data.source.local.entity.MovieEntity
import com.movies.core.data.source.local.entity.ReviewMovieEntity
import com.movies.core.data.source.local.entity.VideoMovieEntity
import com.movies.core.data.source.local.room.MovieDao
import com.movies.core.data.source.local.room.ReviewMovieDao
import com.movies.core.data.source.local.room.VideoMovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val reviewMovieDao: ReviewMovieDao,
    private val videoMovieDao: VideoMovieDao
) {
//    MOVIE
    fun getMoviesByType(type:String):Flow<List<MovieEntity>> = movieDao.getMoviesByType(type)
    suspend fun insertMovies(movieList: List<MovieEntity>) = movieDao.insertMovies(movieList)

//    REVIEW MOVIE
    fun getReviewsMovieByMovieId(movieId:Int):Flow<List<ReviewMovieEntity>> = reviewMovieDao.getMoviesByMovieId(movieId)
    suspend fun insertReviewsMovie(reviewList: List<ReviewMovieEntity>) = reviewMovieDao.insertReviewsMovie(reviewList)

    //    VIDEO MOVIE
    fun getVideosMovieByMovieId(movieId: Int):Flow<List<VideoMovieEntity>> = videoMovieDao.getVideoMoviesByMovieId(movieId)
    suspend fun insertVideosMovie(videoList: List<VideoMovieEntity>) = videoMovieDao.insertVideosMovie(videoList)
}