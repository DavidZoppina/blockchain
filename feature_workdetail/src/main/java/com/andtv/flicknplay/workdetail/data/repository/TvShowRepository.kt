

package com.andtv.flicknplay.workdetail.data.repository

import com.andtv.flicknplay.data.local.datasource.TvShowLocalDataSource
import com.andtv.flicknplay.model.data.local.TvShowDbModel
import com.andtv.flicknplay.model.data.mapper.toDomainModel
import com.andtv.flicknplay.presentation.platform.Resource
import com.andtv.flicknplay.workdetail.R
import com.andtv.flicknplay.workdetail.data.remote.datasource.TvShowRemoteDataSource
import com.andtv.flicknplay.workdetail.data.remote.mapper.toDomainModel
import javax.inject.Inject

/**
 *
 */
class TvShowRepository @Inject constructor(
    private val resource: Resource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource
) {

    fun saveFavoriteTvShow(tvShowDbModel: TvShowDbModel) =
            tvShowLocalDataSource.saveFavoriteTvShow(tvShowDbModel)

    fun deleteFavoriteTvShow(tvShowDbModel: TvShowDbModel) =
            tvShowLocalDataSource.deleteFavoriteTvShow(tvShowDbModel)

    fun isFavoriteTvShow(tvShowDbModel: TvShowDbModel) =
            tvShowLocalDataSource.getById(tvShowDbModel)
                    .isEmpty
                    .map { !it }

    fun getCastByTvShow(tvShowId: Int) =
            tvShowRemoteDataSource.getCastByTvShow(tvShowId)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.casts?.map {
                            cast -> cast.toDomainModel(source)
                        }
                    }

    fun getRecommendationByTvShow(tvShowId: Int, page: Int) =
            tvShowRemoteDataSource.getRecommendationByTvShow(tvShowId, page)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)
                    }

    fun getSimilarByTvShow(tvShowId: Int, page: Int) =
            tvShowRemoteDataSource.getSimilarByTvShow(tvShowId, page)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)
                    }

    fun getReviewByTvShow(tvShowId: Int, page: Int) =
            tvShowRemoteDataSource.getReviewByTvShow(tvShowId, page)
                    .map { it.toDomainModel() }

    fun getVideosByTvShow(tvShowId: Int) =
            tvShowRemoteDataSource.getVideosByTvShow(tvShowId)
                    .map {
                        it.videos?.map { video ->
                            video.toDomainModel()
                        }
                    }
}
