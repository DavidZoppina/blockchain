package com.andtv.flicknplay.workbrowse.data.remote.model.login

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class LoginRequestModel (
        @SerializedName("email") val email : String,
        @SerializedName("password") val password : String,
        @SerializedName("token_name") val tokenName : String
)