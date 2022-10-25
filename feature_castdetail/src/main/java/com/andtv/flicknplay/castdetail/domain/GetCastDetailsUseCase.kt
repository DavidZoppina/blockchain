

package com.andtv.flicknplay.castdetail.domain

import com.andtv.flicknplay.model.domain.CastDomainModel
import com.andtv.flicknplay.model.domain.WorkDomainModel
import io.reactivex.Single
import javax.inject.Inject

/**
 *
 */
class GetCastDetailsUseCase @Inject constructor(
    private val getCastPersonalDetails: GetCastPersonalDetails
) {

    operator fun invoke(castId: Int): Single<Pair<CastDomainModel, List<WorkDomainModel>?>> =
                    getCastPersonalDetails(castId)


}
