package com.andtv.flicknplay.model.data.remote

import com.google.gson.annotations.SerializedName

/**
 *
 */
open class FlicknplayPageResponseWrapper<T>(
    @SerializedName("pagination") var pagination: FlicknplayPageResponse<T>
)
