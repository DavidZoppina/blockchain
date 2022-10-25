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

package com.andtv.flicknplay.model.data.mapper

import com.andtv.flicknplay.model.data.remote.*
import com.andtv.flicknplay.model.domain.HistoryWorkDomainModel
import com.andtv.flicknplay.model.domain.PageDomainModel

fun <T : WorkResponse> PageResponse<T>.toDomainModel(source: String) = PageDomainModel(
        page = page,
        totalPages = totalPages,
        results = results?.map { it.toDomainModel(source) }
)

fun <T : FlicknplayWorkResponse> FlicknplaySearchResponse<T>.toDomainModel(source: String) = PageDomainModel(
        page = 1,
        totalPages = 1,
        results = results?.map { it.toDomainModel(source) }
)

fun <T : FlicknplayWorkResponse> FlicknplayPageResponseWrapper<T>.toDomainModel(source: String) = PageDomainModel(
        page = pagination.page,
        totalPages = pagination.lastPage,
        results = pagination.data?.map { it.toDomainModel(source) }
)

fun <T : FlicknplayWorkResponse> FlicknplayPageResponse<T>.toDomainModel(source: String) = PageDomainModel(
        page = page,
        totalPages = lastPage,
        results = data?.map { it.toDomainModel(source) }
)

fun FlicknplayListPageResponseWrapper.toDomainModel(source: String) = PageDomainModel(
        page = items.page,
        totalPages = items.lastPage,
        results = items.data?.map { it.toDomainModel(source) },
)

fun <T : FlicknplayWorkResponse> RelatedTitlesResponse<T>.toDomainModel(source: String) = PageDomainModel(
        page = 1,
        totalPages = 1,
        results = titles?.map { it.toDomainModel(source) }
)

fun List<HistoryWorkDomainModel>.toPageDomainModel(source: String) = PageDomainModel(
        page = 1,
        totalPages = 1,
        results =  map { it.toWorkDomainModel(source)}
)