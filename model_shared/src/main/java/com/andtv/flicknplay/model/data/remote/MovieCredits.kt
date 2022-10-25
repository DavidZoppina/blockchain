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

package com.andtv.flicknplay.model.data.remote

import com.google.gson.annotations.SerializedName
import com.andtv.flicknplay.model.domain.CastDomainModel

/**
 *
 */
data class MovieCredits(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("poster") var poster: String? = null,
    @SerializedName("model_type") var model_type: String? = null,
    @SerializedName("pivot") var pivot: MovieCreditsPivot? = null
)

fun MovieCredits.toDomainModel() = CastDomainModel(
    id = id ?: 0,
    name = name,
    character = pivot?.character,
    order = pivot?.order ?: 0,
    profilePath = poster
)

fun List<MovieCredits>.toDomainModel() = map { it.toDomainModel() }
