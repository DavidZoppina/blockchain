package com.andtv.flicknplay.workbrowse.data.remote.model.register

import com.google.gson.annotations.SerializedName

data class TwitterRegisterModel(
    @SerializedName("email") val email : String,
    @SerializedName("token_name") val tokenName : String
)
