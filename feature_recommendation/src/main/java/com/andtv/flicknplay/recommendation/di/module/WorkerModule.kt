
package com.andtv.flicknplay.recommendation.di.module

import android.app.Application
import androidx.work.WorkManager
import com.andtv.flicknplay.presentation.di.annotation.BroadcastScope
import dagger.Module
import dagger.Provides

/**
 *
 */
@Module
class WorkerModule {

    @Provides
    @BroadcastScope
    fun provideWorkerManager(application: Application) =
            WorkManager.getInstance(application)
}
