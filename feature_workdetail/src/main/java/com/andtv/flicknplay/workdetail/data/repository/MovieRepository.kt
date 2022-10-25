

package com.andtv.flicknplay.workdetail.data.repository

import com.andtv.flicknplay.data.local.datasource.MovieLocalDataSource
import com.andtv.flicknplay.model.data.local.MovieDbModel
import com.andtv.flicknplay.model.data.mapper.toDomainModel
import com.andtv.flicknplay.presentation.platform.Resource
import com.andtv.flicknplay.workdetail.R
import com.andtv.flicknplay.workdetail.data.remote.datasource.MovieRemoteDataSource
import com.andtv.flicknplay.workdetail.data.remote.mapper.toDomainModel
import javax.inject.Inject

/**
 *
 */
class MovieRepository @Inject constructor(
    private val resource: Resource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) {

    fun saveFavoriteMovie(movieDbModel: MovieDbModel) =
            movieLocalDataSource.saveFavoriteMovie(movieDbModel)

    fun deleteFavoriteMovie(movieDbModel: MovieDbModel) =
            movieLocalDataSource.deleteFavoriteMovie(movieDbModel)

    fun isFavoriteMove(movieDbModel: MovieDbModel) =
            movieLocalDataSource.getById(movieDbModel)
                    .isEmpty
                    .map { !it }

    fun getMovieDetails(movieId: Int, page: Int) =
        movieRemoteDataSource.getMovieDetails(movieId, page)
            .map {
                val source = resource.getStringResource(R.string.source_flicknplay)
                it.toDomainModel(source)
            }


    fun getEpisodesByMovieIDAndSeasonID(movieId: Int, seasonNumber: Int) =
        movieRemoteDataSource.getEpisodesBySeasonNumber(movieId, seasonNumber)
            .map {
                val source = resource.getStringResource(R.string.source_flicknplay)
                it.toDomainModel(source)
            }

    fun getCastByMovie(movieId: Int) =
            movieRemoteDataSource.getCastByMovie(movieId)
                    .map {
                        val source = resource.getStringResource(R.string.source_flicknplay)
                        it.casts?.map { cast ->
                            cast.toDomainModel(source)
                        }
                    }

    fun getRecommendationByMovie(movieId: Int, page: Int) =
            movieRemoteDataSource.getRecommendationByMovie(movieId, page)
                    .map {
                        val source = resource.getStringResource(R.string.source_flicknplay)
                        it.toDomainModel(source)
                    }

    fun getSimilarByMovie(movieId: Int, page: Int) =
            movieRemoteDataSource.getSimilarByMovie(movieId, page)
                    .map {
                        val source = resource.getStringResource(R.string.source_flicknplay)
                        it.toDomainModel(source)
                    }

    fun getReviewByMovie(tvShowId: Int, page: Int) =
            movieRemoteDataSource.getReviewByMovie(tvShowId, page)
                    .map { it.toDomainModel() }

    fun getVideosByMovie(movieId: Int) =
            movieRemoteDataSource.getVideosByMovie(movieId)
                    .map {
                        it.videos?.map { video ->
                            video.toDomainModel()
                        }
                    }

  fun updateVideoHistory(videoId:Int, seekTime: Int, duration:Int) =
            movieRemoteDataSource.updateVideoHistory(videoId, seekTime, duration)


}
