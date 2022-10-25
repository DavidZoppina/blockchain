package com.andtv.flicknplay.workbrowse.di.module

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.data.remote.api.WhoIsWatchingApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class WhoIsWatchingApiModule {

    @Provides
    @FragmentScope
    fun provideWhoIsWatchingApi (@Named("whoIsWatching") retrofit: Retrofit) =
    retrofit.create(WhoIsWatchingApi::class.java)
}