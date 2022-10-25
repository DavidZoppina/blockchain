

package com.andtv.flicknplay.search.data.repository

import com.andtv.flicknplay.model.data.mapper.toDomainModel
import com.andtv.flicknplay.presentation.platform.Resource
import com.andtv.flicknplay.search.R
import com.andtv.flicknplay.search.data.remote.datasource.FlicknplayRemoteDataSource
import javax.inject.Inject

/**
 *
 */
class MovieRepository @Inject constructor(
    private val resource: Resource,
    private val flicknplayRemoteDataSource: FlicknplayRemoteDataSource
) {

    fun searchMoviesByQuery(query: String, page: Int) =
            flicknplayRemoteDataSource.searchMoviesByQuery(query, page)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)
                    }
}
