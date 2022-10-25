

package com.andtv.flicknplay.castdetail.domain

import com.andtv.flicknplay.castdetail.data.repository.CastRepository
import com.andtv.flicknplay.model.domain.CastDomainModel
import com.andtv.flicknplay.model.domain.WorkDomainModel
import io.reactivex.Single
import javax.inject.Inject

/**
 *
 */
class GetCastPersonalDetails @Inject constructor(
    private val castRepository: CastRepository
) {

    operator fun invoke(castId: Int): Single<Pair<CastDomainModel, List<WorkDomainModel>?>> =
            castRepository.getCastDetails(castId)
}
