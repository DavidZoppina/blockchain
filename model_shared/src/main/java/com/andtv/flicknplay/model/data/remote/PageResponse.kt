package com.andtv.flicknplay.model.data.remote

import com.google.gson.annotations.SerializedName

/**
 *
 */
open class PageResponse<T>(
    @SerializedName("page") var page: Int = 0,
    @SerializedName("total_pages") var totalPages: Int = 0,
    @SerializedName("total_results") var totalResults: Int = 0,
    @SerializedName("results") var results: List<T>? = null
)
