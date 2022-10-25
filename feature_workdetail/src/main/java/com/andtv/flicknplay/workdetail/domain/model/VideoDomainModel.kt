/*
 * Copyright (C) 2021 Flicknplay
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

package com.andtv.flicknplay.workdetail.domain.model

import com.andtv.flicknplay.model.domain.VideoDomainModel

/**
 * Copyright (C) Flicknplay 29-10-2019.
 */
data class VideoDomainModel(
    val id: Int? = null,
    val name: String? = null,
    val type: String? = null,
    val url: String? = null,
    var thumbnail: String? = null,
    val order: Int? = null,
    val episodeNumber: Int? = null
)

fun VideoDomainModel.toWorkDetail() = com.andtv.flicknplay.workdetail.domain.model.VideoDomainModel(id, name, type, url, thumbnail, order, episodeNumber)

fun List<VideoDomainModel>.toWorkDetail() = map { it.toWorkDetail()}
