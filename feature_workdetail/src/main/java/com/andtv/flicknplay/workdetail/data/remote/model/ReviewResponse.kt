

package com.andtv.flicknplay.workdetail.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class ReviewResponse(
    @SerializedName("id") val id: String? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("url") val url: String? = null
)
