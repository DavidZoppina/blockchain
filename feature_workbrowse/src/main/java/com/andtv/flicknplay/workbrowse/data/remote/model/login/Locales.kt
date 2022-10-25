package com.andtv.flicknplay.workbrowse.data.remote.model.login
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class Locales (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("language") val language : String
)