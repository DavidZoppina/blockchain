

package com.andtv.flicknplay.workdetail.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class VideoListResponse(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("results") var videos: List<VideoResponse>? = null
)
