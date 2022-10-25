

package com.andtv.flicknplay.recommendation.data.repository

import com.andtv.flicknplay.model.domain.WorkDomainModel
import com.andtv.flicknplay.recommendation.data.local.provider.RecommendationProvider
import io.reactivex.Completable
import javax.inject.Inject

/**
 *
 */
class RecommendationRepository @Inject constructor(
    private val recommendationProvider: RecommendationProvider
) {

    fun loadRecommendations(works: List<WorkDomainModel>?): Completable =
            recommendationProvider.loadRecommendations(works)
}
