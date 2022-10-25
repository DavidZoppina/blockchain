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

package com.andtv.flicknplay.di

import com.andtv.flicknplay.castdetail.di.CastDetailsActivityComponent
import com.andtv.flicknplay.di.module.ApplicationModule
import com.andtv.flicknplay.di.module.SubcomponentsModule
import com.andtv.flicknplay.recommendation.di.BootBroadcastReceiverComponent
import com.andtv.flicknplay.recommendation.di.RecommendationWorkerComponent
import com.andtv.flicknplay.search.di.SearchActivityComponent
import com.andtv.flicknplay.workbrowse.di.MainActivityComponent
import com.andtv.flicknplay.workdetail.di.PlayerActivityComponent
import com.andtv.flicknplay.workdetail.di.WorkDetailsActivityComponent
import dagger.Component
import javax.inject.Singleton

/**
 *
 */
@Singleton
@Component(
        modules = [
            ApplicationModule::class,
            SubcomponentsModule::class
        ]
)
interface ApplicationComponent {

    fun castDetailsActivityComponent(): CastDetailsActivityComponent.Factory

    fun searchActivityComponent(): SearchActivityComponent.Factory

    fun workDetailsActivityComponent(): WorkDetailsActivityComponent.Factory

    fun mainActivityComponent(): MainActivityComponent.Factory

    fun bootBroadcastReceiverComponent(): BootBroadcastReceiverComponent.Factory

    fun recommendationWorkerComponent(): RecommendationWorkerComponent.Factory

    fun playerActivityComponent(): PlayerActivityComponent.Factory
}
