

package com.andtv.flicknplay.application

import android.app.Application
import android.os.StrictMode
import com.andtv.flicknplay.BuildConfig
import com.andtv.flicknplay.castdetail.di.CastDetailsActivityComponentProvider
import com.andtv.flicknplay.di.ApplicationComponent
import com.andtv.flicknplay.di.DaggerApplicationComponent
import com.andtv.flicknplay.di.module.ApplicationModule
import com.andtv.flicknplay.recommendation.di.BootBroadcastReceiverComponentProvider
import com.andtv.flicknplay.recommendation.di.RecommendationWorkerComponentProvider
import com.andtv.flicknplay.search.di.SearchActivityComponentProvider
import com.andtv.flicknplay.workbrowse.di.MainActivityComponentProvider
import com.andtv.flicknplay.workdetail.di.PlayerActivityComponentProvider
import com.andtv.flicknplay.workdetail.di.WorkDetailsActivityComponentProvider
import timber.log.Timber

/**
 *
 */
class FlicknplayApplication : Application(),
        CastDetailsActivityComponentProvider,
        SearchActivityComponentProvider,
        WorkDetailsActivityComponentProvider,
        MainActivityComponentProvider,
        BootBroadcastReceiverComponentProvider,
        RecommendationWorkerComponentProvider,
        PlayerActivityComponentProvider {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        when (BuildConfig.BUILD_TYPE) {
            "debug" -> {
                Timber.plant(Timber.DebugTree())
                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .build())
                StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .detectLeakedClosableObjects()
                        .penaltyLog()
                        .build())
            }
        }

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()

    }

    override fun castDetailsActivityComponent() =
            applicationComponent.castDetailsActivityComponent().create()

    override fun searchActivityComponent() =
            applicationComponent.searchActivityComponent().create()

    override fun workDetailsActivityComponent() =
            applicationComponent.workDetailsActivityComponent().create()

    override fun mainActivityComponent() =
            applicationComponent.mainActivityComponent().create()

    override fun bootBroadcastReceiverComponent() =
            applicationComponent.bootBroadcastReceiverComponent().create()

    override fun recommendationWorkerComponent() =
            applicationComponent.recommendationWorkerComponent().create()

    override fun playerActivityComponent() =
            applicationComponent.playerActivityComponent().create()
}
