package com.andtv.flicknplay.model.data.remote

import com.google.gson.annotations.SerializedName

/**
 *
 */
open class RelatedTitlesResponse<T>(
    @SerializedName("titles") var titles: List<T>? = null
)
