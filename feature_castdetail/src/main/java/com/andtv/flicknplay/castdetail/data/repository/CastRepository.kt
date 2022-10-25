

package com.andtv.flicknplay.castdetail.data.repository

import com.andtv.flicknplay.castdetail.R
import com.andtv.flicknplay.castdetail.data.remote.datasource.CastRemoteDataSource
import com.andtv.flicknplay.model.data.mapper.toDomainModel
import com.andtv.flicknplay.model.domain.CastDomainModel
import com.andtv.flicknplay.model.domain.WorkDomainModel
import com.andtv.flicknplay.presentation.platform.Resource
import io.reactivex.Single
import javax.inject.Inject

/**
 *
 */
class CastRepository @Inject constructor(
    private val resource: Resource,
    private val castRemoteDataSource: CastRemoteDataSource
) {

    fun getCastDetails(castId: Int): Single<Pair<CastDomainModel, List<WorkDomainModel>?>> =
            castRemoteDataSource.getCastDetails(castId)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)!! to it.credits?.works?.map { it.toDomainModel(source) }
                    }

    fun getMovieCreditsByCast(castId: Int) =
            castRemoteDataSource.getMovieCreditsByCast(castId)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)!! to it.credits?.works?.map { it.toDomainModel(source) }
                    }

    fun getTvShowCreditsByCast(castId: Int) =
            castRemoteDataSource.getTvShowCreditsByCast(castId)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)!! to it.credits?.works?.map { it.toDomainModel(source) }
                    }
}
