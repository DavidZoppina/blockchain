

package com.andtv.flicknplay.search.domain

import com.andtv.flicknplay.search.data.repository.MovieRepository
import javax.inject.Inject

/**
 *
 */
class SearchByQueryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(query: String, page: Int) =
            movieRepository.searchMoviesByQuery(query, page)
}
