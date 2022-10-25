

package com.andtv.flicknplay.di.module

import android.app.Application
import android.content.Context
import com.andtv.flicknplay.data.di.module.MediaLocalModule
import com.andtv.flicknplay.data.di.module.MediaRemoteModule
import com.andtv.flicknplay.presentation.BuildConfig
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 *
 */
@Module(
        includes = [
            MediaLocalModule::class,
            MediaRemoteModule::class
        ]
)
class ApplicationModule(
    private val application: Application
) {

    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    @Singleton
    fun provideRxScheduler() = RxScheduler(
            Schedulers.io(),
            Schedulers.computation(),
            AndroidSchedulers.mainThread()
    )

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application) =
            application.getSharedPreferences(BuildConfig.PREFERENCE_NAME, Context.MODE_PRIVATE)
}
