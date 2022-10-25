

package com.andtv.flicknplay.castdetail.domain

import com.andtv.flicknplay.castdetail.data.repository.CastRepository
import javax.inject.Inject

/**
 *
 */
class GetTvShowCreditsByCastUseCase @Inject constructor(
    private val castRepository: CastRepository
) {

    operator fun invoke(castId: Int) =
            castRepository.getTvShowCreditsByCast(castId)
}
