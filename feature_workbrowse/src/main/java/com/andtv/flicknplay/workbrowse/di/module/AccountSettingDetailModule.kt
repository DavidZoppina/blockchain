package com.andtv.flicknplay.workbrowse.di.module

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.data.remote.api.AccountSettingDetailApi
import com.andtv.flicknplay.workbrowse.data.remote.api.AccountSettingsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class AccountSettingDetailModule {

    @Provides
    fun provideAccountSettingDetailApi(@Named("flicknplayRetrofit") retrofit: Retrofit): AccountSettingDetailApi =
        retrofit.create(AccountSettingDetailApi::class.java)

}