package com.andtv.flicknplay.model.data.remote

import com.google.gson.annotations.SerializedName

/**
 *
 */
open class FlicknplayPageResponse<T>(
    @SerializedName("current_page") var page: Int = 0,
    @SerializedName("last_page") var lastPage: Int = 0,
    @SerializedName("per_page") var perPage: Int = 0,
    @SerializedName("data") var data: List<T>? = null
)
