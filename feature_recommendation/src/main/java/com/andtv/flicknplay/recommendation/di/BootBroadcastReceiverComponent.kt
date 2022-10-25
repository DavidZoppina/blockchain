
package com.andtv.flicknplay.recommendation.di

import com.andtv.flicknplay.presentation.di.annotation.BroadcastScope
import com.andtv.flicknplay.recommendation.di.module.WorkerModule
import com.andtv.flicknplay.recommendation.presentation.broadcast.BootBroadcastReceiver
import dagger.Subcomponent

/**
 *
 */
@BroadcastScope
@Subcomponent(
        modules = [
            WorkerModule::class
        ]
)
interface BootBroadcastReceiverComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): BootBroadcastReceiverComponent
    }

    fun inject(broadcastReceiver: BootBroadcastReceiver)
}
