package com.andtv.flicknplay.workbrowse.data.remote.model.register
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterResponse (

	@SerializedName("message") val message : String,
	@SerializedName("status") val status : String
)