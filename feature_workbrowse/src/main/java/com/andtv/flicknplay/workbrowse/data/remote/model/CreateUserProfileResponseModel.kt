package com.andtv.flicknplay.workbrowse.data.remote.model

import com.google.gson.annotations.SerializedName

data class CreateUserProfileResponseModel(
    @SerializedName("profile") val profile: Boolean,
    @SerializedName("status") val success: String
)
