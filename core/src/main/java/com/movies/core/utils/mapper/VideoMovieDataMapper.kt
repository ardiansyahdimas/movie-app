package com.movies.core.utils.mapper

import com.movies.core.data.source.local.entity.VideoMovieEntity
import com.movies.core.data.source.remote.response.ResultsVideoItem
import com.movies.core.domain.model.VideoMovieModel

object VideoMovieDataMapper {
    fun mapResponsesToEntities(movieId:Int,input: List<ResultsVideoItem>): List<VideoMovieEntity> {
        val videoMovieList = ArrayList<VideoMovieEntity>()
        input.map {
            val video = VideoMovieEntity(
                id = it.id,
                movieId = movieId,
                official = it.official,
                site = it.site,
                size = it.size,
                name  = it.name,
                type = it.type,
                key = it.key

            )
            videoMovieList.add(video)
        }
        return videoMovieList
    }

    fun mapEntitiesToDomain(input: List<VideoMovieEntity>): List<VideoMovieModel> =
        input.map {
            VideoMovieModel(
                id = it.id,
                movieId = it.movieId,
                official = it.official,
                site = it.site,
                size = it.size,
                name  = it.name,
                type = it.type,
                key = it.key
            )
        }

    fun mapDomainToEntity(input: VideoMovieModel) =
        VideoMovieEntity(
            id = input.id,
            movieId = input.movieId,
            official = input.official,
            site = input.site,
            size = input.size,
            name  = input.name,
            type = input.type,
            key = input.key
        )
}