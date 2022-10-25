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

package com.andtv.flicknplay.recommendation.presentation.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.andtv.flicknplay.recommendation.di.BootBroadcastReceiverComponentProvider
import com.andtv.flicknplay.recommendation.presentation.presenter.BootPresenter
import javax.inject.Inject
import timber.log.Timber

/**
 *
 */
class BootBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var presenter: BootPresenter

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("Boot initiated")
        (context.applicationContext as BootBroadcastReceiverComponentProvider)
                .bootBroadcastReceiverComponent()
                .inject(this)

        presenter.scheduleRecommendationUpdate()
    }
}
