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

package com.andtv.flicknplay.recommendation.presentation.worker

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.andtv.flicknplay.recommendation.di.RecommendationWorkerComponentProvider
import com.andtv.flicknplay.recommendation.domain.LoadRecommendationUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 *
 */
class RecommendationWorker(
    context: Context,
    workerParameters: WorkerParameters
) : RxWorker(context, workerParameters) {

    @Inject
    lateinit var loadRecommendationUseCase: LoadRecommendationUseCase

    init {
        (context.applicationContext as RecommendationWorkerComponentProvider)
                .recommendationWorkerComponent()
                .inject(this)
    }

    override fun createWork(): Single<Result> =
            loadRecommendationUseCase()
                    .toSingle { Result.success() }
                    .onErrorReturn { Result.failure() }
}
