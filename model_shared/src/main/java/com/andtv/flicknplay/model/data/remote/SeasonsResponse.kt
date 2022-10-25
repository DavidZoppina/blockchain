package com.andtv.flicknplay.model.data.remote

import com.google.gson.annotations.SerializedName
import com.andtv.flicknplay.model.domain.SeasonDomainModel

data class SeasonsResponse(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("number") var number: Int = 0,
    @SerializedName("episode_count") var episodeCount: Int = 0,
    @SerializedName("title_id") var titleId: Int = 0,
    @SerializedName("model_type") var modelType: String?,

)

fun SeasonsResponse.toDomainModel() = SeasonDomainModel(
    id = id,
    number = number,
    episodeCount = episodeCount,
    titleId = titleId,
    modelType = modelType
)

fun List<SeasonsResponse>.toDomainModel() = map { it.toDomainModel() }