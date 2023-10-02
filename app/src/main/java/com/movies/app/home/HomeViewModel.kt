package com.movies.app.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.movies.core.data.Resource
import com.movies.core.domain.model.MovieModel
import com.movies.core.domain.model.ReviewMovieModel
import com.movies.core.domain.model.VideoMovieModel
import com.movies.core.domain.usecase.MovieUseCase
import com.movies.core.utils.Config
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val movieUseCase: MovieUseCase) : ViewModel() {
    var resultValueNowPlaying : LiveData<Resource<List<MovieModel>>>? =null
    var resultValuePopuler : LiveData<Resource<List<MovieModel>>>? =null
    var resultValueTopRated : LiveData<Resource<List<MovieModel>>>? =null
    var resultValueUpcoming : LiveData<Resource<List<MovieModel>>>? =null
    var resultReviewsMovie : LiveData<Resource<List<ReviewMovieModel>>>?  = null
    var resultVideosMovie : LiveData<Resource<List<VideoMovieModel>>>?  = null

    fun getMovies(type:String, page:Int) {
        when(type) {
         Config.NOW_PLAYING_MOVIES -> resultValueNowPlaying = movieUseCase.getMovies(type, page).asLiveData()
         Config.POPULAR_MOVIES -> resultValuePopuler = movieUseCase.getMovies(type, page).asLiveData()
         Config.TOP_RATED_MOVIES -> resultValueTopRated = movieUseCase.getMovies(type, page).asLiveData()
         else -> resultValueUpcoming = movieUseCase.getMovies(type, page).asLiveData()
        }
    }

    fun getReviewsMovieByMovieId(movieId:Int, page: Int) {
        resultReviewsMovie = movieUseCase.getReviewsMovieByMovieId(movieId, page).asLiveData()
    }

    fun getVideosMovieByMovieId(movieId: Int) {
        resultVideosMovie = movieUseCase.getVideosMovieByMovieId(movieId).asLiveData()
    }
}