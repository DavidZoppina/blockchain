package com.andtv.flicknplay.workbrowse.data.remote.model
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponse (
	@SerializedName("errors") val errors : Errors
)