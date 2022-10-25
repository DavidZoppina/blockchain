

package com.andtv.flicknplay.recommendation.di.module

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.andtv.flicknplay.presentation.di.annotation.WorkerScope
import com.andtv.flicknplay.recommendation.data.local.provider.channel.RecommendationChannelApi
import com.andtv.flicknplay.recommendation.data.local.provider.row.RecommendationRowApi
import com.andtv.flicknplay.recommendation.data.local.sharedpreferences.LocalSettings
import com.andtv.flicknplay.route.workbrowse.WorkBrowseRoute
import com.andtv.flicknplay.route.workdetail.WorkDetailsRoute
import dagger.Module
import dagger.Provides

/**
 *
 */
@Module
class RecommendationModule {

    @Provides
    @WorkerScope
    fun provideNotificationManager(application: Application) =
            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Provides
    @WorkerScope
    fun provideRecommendationManager(
        application: Application,
        localSettings: LocalSettings,
        notificationManager: NotificationManager,
        workDetailsRoute: WorkDetailsRoute,
        workBrowseRoute: WorkBrowseRoute
    ) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                RecommendationChannelApi(application, localSettings, workDetailsRoute, workBrowseRoute)
            else
                RecommendationRowApi(application, notificationManager, workDetailsRoute)
}
