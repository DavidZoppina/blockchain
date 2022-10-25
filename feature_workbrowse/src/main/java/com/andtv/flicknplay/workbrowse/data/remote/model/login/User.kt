package com.andtv.flicknplay.workbrowse.data.remote.model.login
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class User (

	@SerializedName("id") val id : Int,
	@SerializedName("username") val username : String,
	@SerializedName("first_name") val first_name : String,
	@SerializedName("last_name") val last_name : String,
	@SerializedName("avatar_url") val avatar_url : String,
	@SerializedName("gender") val gender : String,
	@SerializedName("email") val email : String,
	@SerializedName("card_brand") val card_brand : String,
	@SerializedName("card_last_four") val card_last_four : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("background") val background : String,
	@SerializedName("language") val language : String,
	@SerializedName("country") val country : String,
	@SerializedName("timezone") val timezone : String,
	@SerializedName("avatar") val avatar : String,
	@SerializedName("stripe_id") val stripe_id : String,
	@SerializedName("available_space") val available_space : String,
	@SerializedName("email_verified_at") val email_verified_at : String,
	@SerializedName("profile_name") val profile_name : String,
	@SerializedName("access_token") val access_token : String,
	@SerializedName("display_name") val display_name : String,
	@SerializedName("has_password") val has_password : Boolean,
	@SerializedName("model_type") val model_type : String
)