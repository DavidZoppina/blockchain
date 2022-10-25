

package com.andtv.flicknplay.castdetail.data.remote.datasource

import com.andtv.flicknplay.castdetail.data.remote.api.CastFlicknplayApi
import com.andtv.flicknplay.castdetail.data.remote.api.CastTmdbApi
import com.andtv.flicknplay.model.data.remote.CastResponseWrapper
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

/**
 *
 */
class CastRemoteDataSource @Inject constructor(
    @Named("tmdbApiKey") private val tmdbApiKey: String,
    @Named("tmdbFilterLanguage") private val tmdbFilterLanguage: String,
    private val castTmdbApi: CastTmdbApi,
    private val flicknplayApi: CastFlicknplayApi
) {

    fun getCastDetails(castId: Int): Single<CastResponseWrapper> =
        flicknplayApi.getCastDetails(castId)


    fun getMovieCreditsByCast(castId: Int) =
        flicknplayApi.getMovieCredits(castId)
   //fun getMovieCreditsByCast(castId: Int) =
   //       castTmdbApi.getMovieCredits(castId, tmdbApiKey, tmdbFilterLanguage)

    fun getTvShowCreditsByCast(castId: Int): Single<CastResponseWrapper> =
        flicknplayApi.getTvShowCredits(castId) as Single<CastResponseWrapper>

    //fun getTvShowCreditsByCast(castId: Int) =
      //    castTmdbApi.getTvShowCredits(castId, tmdbApiKey, tmdbFilterLanguage)
}
