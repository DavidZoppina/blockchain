package com.andtv.flicknplay.model.data.remote

import com.google.gson.annotations.SerializedName

/**
 *
 */
open class FlicknplayListPageResponseWrapper(
    @SerializedName("list") var list: FlicknplayListResponseData,
    @SerializedName("items") var items: FlicknplayPageResponse<FlicknplayMovieResponse>
)
