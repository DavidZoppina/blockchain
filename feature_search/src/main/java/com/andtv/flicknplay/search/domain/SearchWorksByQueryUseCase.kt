

package com.andtv.flicknplay.search.domain

import javax.inject.Inject

/**
 *
 */
class SearchWorksByQueryUseCase @Inject constructor(
    private val urlEncoderTextUseCase: UrlEncoderTextUseCase,
    private val searchByQueryUseCase: SearchByQueryUseCase
) {

    operator fun invoke(query: String) =
            urlEncoderTextUseCase(query)
                    .flatMap {
                        searchByQueryUseCase(it, 1)
                    }
}
