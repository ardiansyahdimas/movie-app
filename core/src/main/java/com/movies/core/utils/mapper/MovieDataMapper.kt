package com.movies.core.utils.mapper

import com.movies.core.data.source.local.entity.MovieEntity
import com.movies.core.data.source.remote.response.ResultsMovieItem
import com.movies.core.domain.model.MovieModel

object MovieDataMapper {
    fun mapResponsesToEntities(type:String,input: List<ResultsMovieItem>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                adult = it.adult,
                backdrop_path = it.backdropPath,
                original_language = it.originalLanguage,
                original_title = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.posterPath,
                release_date = it.releaseDate,
                title = it.title,
                video = it.video,
                vote_average = it.voteAverage,
                vote_count = it.voteCount,
                type = type

            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<MovieModel> =
        input.map {
            MovieModel(
                id = it.id,
                adult = it.adult,
                backdrop_path = it.backdrop_path,
                original_language = it.original_language,
                original_title = it.original_title,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                video = it.video,
                vote_average = it.vote_average,
                vote_count = it.vote_count,
                type = it.type
            )
        }

    fun mapDomainToEntity(input: MovieModel) =
        MovieEntity(
            id = input.id,
            adult = input.adult,
            backdrop_path = input.backdrop_path,
            original_language = input.original_language,
            original_title = input.original_title,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            release_date = input.release_date,
            title = input.title,
            video = input.video,
            vote_average = input.vote_average,
            vote_count = input.vote_count,
            type = input.type
        )
}