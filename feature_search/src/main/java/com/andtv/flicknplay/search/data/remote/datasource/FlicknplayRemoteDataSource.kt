

package com.andtv.flicknplay.search.data.remote.datasource

import com.andtv.flicknplay.search.data.remote.api.SearchFlicknplayApi
import javax.inject.Inject
import javax.inject.Named

/**
 *.
 */
class FlicknplayRemoteDataSource @Inject constructor(
    @Named("tmdbApiKey") private val tmdbApiKey: String,
    @Named("tmdbFilterLanguage") private val tmdbFilterLanguage: String,
    private val searchFlicknplayApi: SearchFlicknplayApi
) {

    fun searchMoviesByQuery(query: String, page: Int) =
            searchFlicknplayApi.searchMoviesByQuery(query)
}
