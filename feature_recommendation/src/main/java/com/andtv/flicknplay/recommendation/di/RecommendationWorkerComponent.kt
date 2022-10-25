

package com.andtv.flicknplay.recommendation.di

import com.andtv.flicknplay.presentation.di.annotation.WorkerScope
import com.andtv.flicknplay.recommendation.di.module.MovieApiModule
import com.andtv.flicknplay.recommendation.di.module.RecommendationModule
import com.andtv.flicknplay.recommendation.presentation.worker.RecommendationWorker
import dagger.Subcomponent

/**
 *
 */
@WorkerScope
@Subcomponent(
        modules = [
            MovieApiModule::class,
            RecommendationModule::class
        ]
)
interface RecommendationWorkerComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RecommendationWorkerComponent
    }

    fun inject(recommendationWorker: RecommendationWorker)
}
