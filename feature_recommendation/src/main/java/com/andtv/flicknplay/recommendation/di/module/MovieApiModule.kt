

package com.andtv.flicknplay.recommendation.di.module

import com.andtv.flicknplay.presentation.di.annotation.WorkerScope
import com.andtv.flicknplay.recommendation.data.remote.api.MovieTmdbApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 *
 */
@Module
class MovieApiModule {

    @Provides
    @WorkerScope
    fun provideMovieApi(@Named("tmdbRetrofit") retrofit: Retrofit) =
            retrofit.create(MovieTmdbApi::class.java)
}
