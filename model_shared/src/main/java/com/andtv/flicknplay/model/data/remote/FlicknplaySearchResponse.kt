package com.andtv.flicknplay.model.data.remote

import com.google.gson.annotations.SerializedName

/**
 *
 */
open class FlicknplaySearchResponse<T>(
    @SerializedName("status") var status: String = "",
    @SerializedName("query") var query: String = "",
    @SerializedName("results") var results: List<T>? = null
)
