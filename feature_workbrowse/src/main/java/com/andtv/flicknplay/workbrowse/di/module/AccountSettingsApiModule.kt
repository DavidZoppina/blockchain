package com.andtv.flicknplay.workbrowse.di.module

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.data.remote.api.AccountSettingsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class AccountSettingsApiModule {

    @Provides
    @FragmentScope
    fun provideAccountSettingsApi(@Named ("flicknplayRetrofit") retrofit: Retrofit) =
        retrofit.create(AccountSettingsApi::class.java)

}