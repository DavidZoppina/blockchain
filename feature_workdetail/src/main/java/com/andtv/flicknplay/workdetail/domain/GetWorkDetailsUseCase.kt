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

package com.andtv.flicknplay.workdetail.domain

import com.andtv.flicknplay.model.data.remote.toDomainModel
import com.andtv.flicknplay.model.domain.CastDomainModel
import com.andtv.flicknplay.model.domain.PageDomainModel
import com.andtv.flicknplay.model.domain.WorkDomainModel
import com.andtv.flicknplay.model.presentation.model.WorkViewModel
import com.andtv.flicknplay.workdetail.domain.model.ReviewDomainModel
import com.andtv.flicknplay.workdetail.domain.model.VideoDomainModel
import com.andtv.flicknplay.workdetail.domain.model.toWorkDetail
import io.reactivex.Single
import javax.inject.Inject

/**
 * Copyright (C) Flicknplay 20-05-2019.
 */
class GetWorkDetailsUseCase @Inject constructor(
    private val checkFavoriteWorkUseCase: CheckFavoriteWorkUseCase,
    private val getDetailsUseCase: GetDetailsUseCase,
    private val getRecommendationByWorkUseCase: GetRecommendationByWorkUseCase,
    private val getSimilarByWorkUseCase: GetSimilarByWorkUseCase,
    private val getReviewByWorkUseCase: GetReviewByWorkUseCase,
) {

    operator fun invoke(workViewModel: WorkViewModel): Single<WorkDetailsDomainWrapper> =
            Single.zip(
                    checkFavoriteWorkUseCase(workViewModel),
                    getDetailsUseCase(workViewModel.id),
                    //getRecommendationByWorkUseCase(workViewModel.type, workViewModel.id, 1),
                    getSimilarByWorkUseCase(workViewModel.type, workViewModel.id, 1),
            )
            //getReviewByWorkUseCase(workViewModel.type, workViewModel.id, 1),
            { isFavorite, movieDetails, similar->
                WorkDetailsDomainWrapper(
                    isFavorite,
                    movieDetails.videos?.toWorkDetail().also {
                        it?.forEach { it.thumbnail = movieDetails.backdropPath }
                    },
                    movieDetails.credits?.toDomainModel(),
                    PageDomainModel(0, 0),
                    similar,
                    PageDomainModel(0, 0),
                    movieDetails.releaseDate,
                    movieDetails.overview,
                    movieDetails.backdropPath,
                    Pair(movieDetails.isSeries, if(movieDetails.seasons == null) 0 else movieDetails.seasons!!.size)
                )

            }

    data class WorkDetailsDomainWrapper(
        val isFavorite: Boolean,
        val videos: List<VideoDomainModel>?,
        val casts: List<CastDomainModel>?,
        val recommended: PageDomainModel<WorkDomainModel>,
        val similar: PageDomainModel<WorkDomainModel>,
        val reviews: PageDomainModel<ReviewDomainModel>,
        val releaseDate: String?,
        val overview: String?,
        val backDropUrl: String?,
        val series:Pair<Boolean?, Int>?
    )
}
