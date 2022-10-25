

package com.andtv.flicknplay.search.di.module

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.search.data.remote.api.SearchFlicknplayApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 *
 */
@Module
class FlicknplayRemoteDataSourceModule {

    @Provides
    @FragmentScope
    fun provideSearchApi(@Named("flicknplayRetrofit") retrofit: Retrofit) =
            retrofit.create(SearchFlicknplayApi::class.java)
}
