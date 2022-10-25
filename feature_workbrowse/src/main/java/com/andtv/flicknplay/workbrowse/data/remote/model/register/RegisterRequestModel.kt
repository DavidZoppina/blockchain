package com.andtv.flicknplay.workbrowse.data.remote.model.register

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class RegisterRequestModel (
        @SerializedName("email") val email : String,
        @SerializedName("password") val password : String,
        @SerializedName("password_confirmation") val passwordConfirmation : String,
        @SerializedName("token_name") val tokenName : String

)