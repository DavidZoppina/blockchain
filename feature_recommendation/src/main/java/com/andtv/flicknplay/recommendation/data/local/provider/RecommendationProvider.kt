

package com.andtv.flicknplay.recommendation.data.local.provider

import com.andtv.flicknplay.model.domain.WorkDomainModel
import io.reactivex.Completable

/**
 *
 */
interface RecommendationProvider {

    fun loadRecommendations(works: List<WorkDomainModel>?): Completable
}
