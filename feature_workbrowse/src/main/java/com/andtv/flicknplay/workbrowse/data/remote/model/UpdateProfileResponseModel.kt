package com.andtv.flicknplay.workbrowse.data.remote.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponseModel(
    @SerializedName("profile") val profile: Int,
    @SerializedName("status") val success: String
)
