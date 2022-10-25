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

/**
 *
 */
abstract class FlicknplayHistoryWorkResponse(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("description") var overview: String? = null,
    @SerializedName("backdrop") var backdropPath: String? = null,
    @SerializedName("poster") var posterPath: String? = null,
    @SerializedName("popularity") var popularity: Float = 0f,
    @SerializedName("vote_average") var voteAverage: Float = 0f,
    @SerializedName("vote_count") var voteCount: Float = 0f,
    @SerializedName("adult") var isAdult: Boolean = false,
    @SerializedName("videos") var videos: MovieVideo? = null,
    @SerializedName("credits") var credits: List<MovieCredits>? = null,
    @SerializedName("is_series") var isSeries: Boolean? = null,
    @SerializedName("episode_count") var episodeCount:Int = 0,
    @SerializedName("seasons") var seasons:List<SeasonsResponse>? = null,
    @SerializedName("seek_time") var seekTime:Int? = null

) {

    abstract var title: String?

    abstract var originalTitle: String?

    abstract var releaseDate: String?
}
