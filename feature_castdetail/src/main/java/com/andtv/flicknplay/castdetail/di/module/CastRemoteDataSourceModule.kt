

package com.andtv.flicknplay.castdetail.di.module

import com.andtv.flicknplay.castdetail.data.remote.api.CastFlicknplayApi
import com.andtv.flicknplay.castdetail.data.remote.api.CastTmdbApi
import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 *
 */
@Module
class CastRemoteDataSourceModule {

    @Provides
    @FragmentScope
    fun provideCastApi(@Named("tmdbRetrofit") retrofit: Retrofit) =
        retrofit.create(CastTmdbApi::class.java)

    @Provides
    @FragmentScope
    fun provideFlicknplayCastApi(@Named("flicknplayRetrofit") retrofit: Retrofit) =
        retrofit.create(CastFlicknplayApi::class.java)
}
