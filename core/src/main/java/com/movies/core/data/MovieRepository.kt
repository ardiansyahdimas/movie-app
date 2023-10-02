package com.movies.core.data

import com.movies.core.NetworkBoundResource
import com.movies.core.data.source.local.LocalDataSource
import com.movies.core.data.source.remote.RemoteDataSource
import com.movies.core.data.source.remote.network.ApiResponse
import com.movies.core.data.source.remote.response.ResultsMovieItem
import com.movies.core.data.source.remote.response.ResultsReviewItem
import com.movies.core.data.source.remote.response.ResultsVideoItem
import com.movies.core.domain.model.MovieModel
import com.movies.core.domain.model.ReviewMovieModel
import com.movies.core.domain.model.VideoMovieModel
import com.movies.core.domain.repository.IMovieRepository
import com.movies.core.utils.mapper.MovieDataMapper
import com.movies.core.utils.mapper.ReviewMovieDataMapper
import com.movies.core.utils.mapper.VideoMovieDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IMovieRepository {
    override fun getMovies(type:String, page:Int): Flow<Resource<List<MovieModel>>> =
        object : NetworkBoundResource<List<MovieModel>, List<ResultsMovieItem>>() {
            override fun loadFromDB(): Flow<List<MovieModel>>  {
                return localDataSource.getMoviesByType(type).map { MovieDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<MovieModel>?): Boolean =
//                data.isNullOrEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsMovieItem>>> = remoteDataSource.getMovies(type, page)


            override suspend fun saveCallResult(data: List<ResultsMovieItem>) {
                val movieList = MovieDataMapper.mapResponsesToEntities(type,data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getReviewsMovieByMovieId(movieId:Int, page: Int): Flow<Resource<List<ReviewMovieModel>>> =
        object : NetworkBoundResource<List<ReviewMovieModel>, List<ResultsReviewItem>>() {
            override fun loadFromDB(): Flow<List<ReviewMovieModel>>  {
                return localDataSource.getReviewsMovieByMovieId(movieId).map { ReviewMovieDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<ReviewMovieModel>?): Boolean =
//                data.isNullOrEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsReviewItem>>> = remoteDataSource.getReviewsMovieByMovieId(movieId, page)


            override suspend fun saveCallResult(data: List<ResultsReviewItem>) {
                val reviewMovieList = ReviewMovieDataMapper.mapResponsesToEntities(movieId,data)
                localDataSource.insertReviewsMovie(reviewMovieList)
            }
        }.asFlow()

    override fun getVideosMovieByMovieId(movieId:Int): Flow<Resource<List<VideoMovieModel>>> =
        object : NetworkBoundResource<List<VideoMovieModel>, List<ResultsVideoItem>>() {
            override fun loadFromDB(): Flow<List<VideoMovieModel>>  {
                return localDataSource.getVideosMovieByMovieId(movieId).map { VideoMovieDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<VideoMovieModel>?): Boolean =
//                data.isNullOrEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsVideoItem>>> = remoteDataSource.getVideosMovieByMovieId(movieId)


            override suspend fun saveCallResult(data: List<ResultsVideoItem>) {
                val videoMovieList = VideoMovieDataMapper.mapResponsesToEntities(movieId,data)
                localDataSource.insertVideosMovie(videoMovieList)
            }
        }.asFlow()

}