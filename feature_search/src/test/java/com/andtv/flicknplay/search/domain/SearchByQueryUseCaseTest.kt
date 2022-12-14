/*
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.andtv.flicknplay.search.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.andtv.flicknplay.model.domain.PageDomainModel
import com.andtv.flicknplay.model.domain.WorkDomainModel
import com.andtv.flicknplay.search.data.repository.MovieRepository
import io.reactivex.Single
import org.junit.Test

/**
 *
 */
private const val QUERY = "Batman"
private val WORK_PAGE = PageDomainModel(
        page = 1,
        totalPages = 1,
        results = listOf(
                WorkDomainModel(
                        id = 1,
                        title = "Batman",
                        originalTitle = "Batman"
                )
        )
)

class SearchByQueryUseCaseTest {

    private val movieRepository: MovieRepository = mock()
    private val useCase = SearchByQueryUseCase(
            movieRepository
    )

    @Test
    fun `should return the right data when searching movies by query`() {
        whenever(movieRepository.searchMoviesByQuery(QUERY, 1))
                .thenReturn(Single.just(WORK_PAGE))

        useCase(QUERY, 1)
                .test()
                .assertComplete()
                .assertResult(WORK_PAGE)
    }

    @Test
    fun `should return an error when some exception happens`() {
        whenever(movieRepository.searchMoviesByQuery(QUERY, 1))
                .thenReturn(Single.error(Throwable()))

        useCase(QUERY, 1)
                .test()
                .assertError(Throwable::class.java)
    }
}
