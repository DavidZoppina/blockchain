/*
 * Copyright (C) 2021 Flicknplay
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.andtv.flicknplay.workbrowse.data.repository

import com.andtv.flicknplay.data.local.datasource.TvShowLocalDataSource
import com.andtv.flicknplay.model.data.mapper.toDomainModel
import com.andtv.flicknplay.model.domain.WorkDomainModel
import com.andtv.flicknplay.presentation.platform.Resource
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.data.remote.datasource.TvShowRemoteDataSource
import javax.inject.Inject

/**
 * Copyright (C) Flicknplay 20-10-2019.
 */
class TvShowRepository @Inject constructor(
    private val resource: Resource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource
) {

    fun getFavoriteTvShows() =
            tvShowLocalDataSource.getTvShows()
                    .map {
                        val tvShows = mutableListOf<WorkDomainModel>()
                        it.forEach { tvShowDbModel ->
                            tvShowRemoteDataSource.getTvShow(tvShowDbModel.id)?.let { work ->
                                val source = resource.getStringResource(R.string.source_tmdb)
                                val workDomainModel = work.toDomainModel(source).apply {
                                    isFavorite = true
                                }

                                tvShows.add(workDomainModel)
                            }
                        }
                        tvShows.toList()
                    }

    fun getTvShowByGenre(genre: String?, page: Int) =
            tvShowRemoteDataSource.getTvShowByGenre(genre, page)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)
                    }

    fun getAiringTodayTvShows(page: Int) =
            tvShowRemoteDataSource.getAiringTodayTvShows(page)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)
                    }

    fun getOnTheAirTvShows(page: Int) =
            tvShowRemoteDataSource.getOnTheAirTvShows(page)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)
                    }

    fun getPopularTvShows(page: Int) =
            tvShowRemoteDataSource.getPopularTvShows(page)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)
                    }

    fun getTopRatedTvShows(page: Int) =
            tvShowRemoteDataSource.getTopRatedTvShows(page)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)
                    }
}
