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
import io.reactivex.Single
import org.junit.Test

/**
 *
 */
private const val QUERY = "Batman"
private const val QUERY_ENCODED = "Batman"
private val SEARCH_PAGE_VIEW_MODEL = PageDomainModel(
        page = 1,
        totalPages = 10,
        results = listOf(
                WorkDomainModel(
                        id = 1,
                        title = "Batman",
                        originalTitle = "Batman",
                        type = WorkDomainModel.Type.MOVIE
                )
        )
)


class SearchWorksByQueryUseCaseTest {

    private val urlEncoderTextUseCase: UrlEncoderTextUseCase = mock()
    private val searchByQueryUseCase: SearchByQueryUseCase = mock()
    private val useCase = SearchWorksByQueryUseCase(
            urlEncoderTextUseCase,
            searchByQueryUseCase
    )

    @Test
    fun `should return the right data when searching works by query`() {
        val result = SEARCH_PAGE_VIEW_MODEL

        whenever(urlEncoderTextUseCase(QUERY)).thenReturn(Single.just(QUERY_ENCODED))
        whenever(searchByQueryUseCase(QUERY, 1)).thenReturn(Single.just(SEARCH_PAGE_VIEW_MODEL))

        useCase(QUERY)
                .test()
                .assertComplete()
                .assertResult(result)
    }

    @Test
    fun `should return an error when some exception happens`() {
        whenever(urlEncoderTextUseCase(QUERY)).thenReturn(Single.just(QUERY_ENCODED))
        whenever(searchByQueryUseCase(QUERY, 1)).thenReturn(Single.error(Throwable()))

        useCase(QUERY)
                .test()
                .assertError(Throwable::class.java)
    }
}
