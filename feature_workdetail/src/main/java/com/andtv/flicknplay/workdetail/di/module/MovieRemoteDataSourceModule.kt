

package com.andtv.flicknplay.workdetail.di.module

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workdetail.data.remote.api.MovieDetailFlicknplayApi
import com.andtv.flicknplay.workdetail.data.remote.api.MovieDetailTmdbApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 *
 */
@Module
class MovieRemoteDataSourceModule {

    @Provides
    @FragmentScope
    fun provideMovieDetailApi(@Named("tmdbRetrofit") retrofit: Retrofit) =
        retrofit.create(MovieDetailTmdbApi::class.java)

    @Provides
    @FragmentScope
    fun provideFlicknplayMovieDetailApi(@Named("flicknplayRetrofit") retrofit: Retrofit) =
        retrofit.create(MovieDetailFlicknplayApi::class.java)
}
