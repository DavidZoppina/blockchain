package com.andtv.flicknplay.workbrowse.data.remote.model.login
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponse (

	@SerializedName("themes") val themes : Themes,
	@SerializedName("user") val user : User,
	@SerializedName("menus") val menus : List<String>,
	@SerializedName("locales") val locales : List<Locales>,
	@SerializedName("status") val status : String
)