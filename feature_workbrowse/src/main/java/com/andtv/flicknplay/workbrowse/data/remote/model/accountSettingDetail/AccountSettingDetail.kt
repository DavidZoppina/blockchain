package com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail

import com.google.gson.annotations.SerializedName

data class AccountSettingDetailResponse(
    val status: String,
    val user: User
)


data class User(
    val available_space: Any?,
    val avatar: String?,
    val avatar_url: Any?,
    val background: Any?,
    val card_brand: Any?,
    val card_last_four: String?,
    val country: String?,
    val created_at: String?,
    val display_name: String?,
    val email: String?,
    val email_verified_at: String?,
    val first_name: String?,
    val gender: Any?,
    val has_password: Boolean,
    val id: Int,
    val language: String?,
    val last_name: String?,
    val model_type: String?,
    val profile_name: String?,
    val stripe_id: Any?,
    val timezone: String?,
    val updated_at: String?,
    val username: String?
)

data class AccountDetailRequest(
    @SerializedName("first_name") val first_name: String?,
    @SerializedName("last_name") val last_name : String?,
    @SerializedName("avatar") val avatar : String?,
    @SerializedName("language") val language : String?,
    @SerializedName("country") val country : String?,
    @SerializedName("timezone") val timezone : String?,
)

data class ResetPasswordRequest(
    @SerializedName("email") val email: String?,
    @SerializedName("new_password") val new_password : String?,
    @SerializedName("current_password") val current_password : String?,
    @SerializedName("new_password_confirmation") val new_password_confirmation : String?
)
