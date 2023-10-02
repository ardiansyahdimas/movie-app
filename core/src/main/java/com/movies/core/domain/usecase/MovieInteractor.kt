package com.movies.core.domain.usecase

import com.movies.core.data.Resource
import com.movies.core.domain.model.MovieModel
import com.movies.core.domain.model.ReviewMovieModel
import com.movies.core.domain.model.VideoMovieModel
import com.movies.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository):
    MovieUseCase {

    override fun getMovies(type:String, page:Int): Flow<Resource<List<MovieModel>>> = movieRepository.getMovies(type, page)
    override fun getReviewsMovieByMovieId(movieId: Int, page: Int): Flow<Resource<List<ReviewMovieModel>>> = movieRepository.getReviewsMovieByMovieId(movieId, page)
    override fun getVideosMovieByMovieId(movieId: Int): Flow<Resource<List<VideoMovieModel>>> = movieRepository.getVideosMovieByMovieId(movieId)
}