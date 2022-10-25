

package com.andtv.flicknplay.workdetail.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class VideoResponse(
    @SerializedName("id") var id: String? = null,
    @SerializedName("key") var key: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("site") var site: String? = null,
    @SerializedName("type") var type: String? = null
)
