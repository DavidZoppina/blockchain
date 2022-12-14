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

package com.andtv.flicknplay.castdetail.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.andtv.flicknplay.castdetail.data.repository.CastRepository
import com.andtv.flicknplay.model.domain.WorkDomainModel
import io.reactivex.Single
import org.junit.Test

/**
 *
 */
private const val CAST_ID = 1
private val TV_SHOW = WorkDomainModel(
        id = 1,
        title = "Arrow",
        originalTitle = "Arrow"
)

class GetTvShowCreditsByCastUseCaseTest {

    private val castRepository: CastRepository = mock()

    private val useCase = GetTvShowCreditsByCastUseCase(
            castRepository
    )

    @Test
    fun `should return the right data when loading the tv shows by cast`() {
        val castTvShowList = listOf(TV_SHOW)

        whenever(castRepository.getTvShowCreditsByCast(CAST_ID))
                .thenReturn(Single.just(castTvShowList))

        useCase(CAST_ID)
                .test()
                .assertComplete()
                .assertResult(castTvShowList)
    }

    @Test
    fun `should return an error when some exception happens`() {
        whenever(castRepository.getTvShowCreditsByCast(CAST_ID))
                .thenReturn(Single.error(Throwable()))

        useCase(CAST_ID)
                .test()
                .assertError(Throwable::class.java)
    }
}
