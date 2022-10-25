

package com.andtv.flicknplay.di.module

import com.andtv.flicknplay.castdetail.di.CastDetailsActivityComponent
import com.andtv.flicknplay.recommendation.di.BootBroadcastReceiverComponent
import com.andtv.flicknplay.recommendation.di.RecommendationWorkerComponent
import com.andtv.flicknplay.search.di.SearchActivityComponent
import com.andtv.flicknplay.workbrowse.di.MainActivityComponent
import com.andtv.flicknplay.workdetail.di.PlayerActivityComponent
import com.andtv.flicknplay.workdetail.di.WorkDetailsActivityComponent
import dagger.Module

/**
 *
 */
@Module(
        subcomponents = [
            CastDetailsActivityComponent::class,
            SearchActivityComponent::class,
            WorkDetailsActivityComponent::class,
            MainActivityComponent::class,
            BootBroadcastReceiverComponent::class,
            RecommendationWorkerComponent::class,
            PlayerActivityComponent::class
        ]
)
class SubcomponentsModule
