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
import com.andtv.flicknplay.model.domain.VideoDomainModel

/**
 *
 */
data class MovieVideo(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("thumbnail") var thumbnail: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("order") var order: Int? = null,
    @SerializedName("episode_num") var episodeNumber: Int? = null
)

fun MovieVideo.toDomainModel() = VideoDomainModel(
    id = id,
    name = name,
    url = url,
    thumbnail = thumbnail,
    type = type,
    order = order,
    episodeNumber = episodeNumber
)

fun List<MovieVideo>.toDomainModel() = map { it.toDomainModel() }
