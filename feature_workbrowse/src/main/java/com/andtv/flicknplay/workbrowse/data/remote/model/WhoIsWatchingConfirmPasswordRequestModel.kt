package com.andtv.flicknplay.workbrowse.data.remote.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WhoIsWatchingConfirmPasswordRequestModel(
     @SerializedName ("id") val id: String,
     @SerializedName ("password") val password: String
)
