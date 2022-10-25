package com.andtv.flicknplay.workbrowse.data.remote.model

import com.google.gson.annotations.SerializedName

data class SocialLoginModel(

    @SerializedName("id") var id : String? = null,
    @SerializedName("name") var name : String?= null,
    @SerializedName("email") var email : String?= null,
    @SerializedName("avatar") var avatar : String?= null,
    @SerializedName("profileUrl") var profileUrl : String?= null,
    @SerializedName("token_name") var token : String?= null,
    @SerializedName("refreshToken") var refreshToken : String?= null,
    @SerializedName("expiresIn") var expiresIn : String?= null,
    @SerializedName("user_id") var user_id : Int? = null

)

data class SocialResponse(
    @SerializedName("data") val data : SocialData?,
    @SerializedName("message") val message : String?,
    @SerializedName("status") val status : Int?

)

data class SocialData(
    @SerializedName("service_name") val service_name : String?,
     @SerializedName("user_service_id") val user_service_id : String?,
     @SerializedName("user_id") val user_id : Int?,
     @SerializedName("username") val username : String?,
     @SerializedName("updated_at") val updated_at : String?,
     @SerializedName("created_at") val created_at : String?,
     @SerializedName("id") val id : Int?
)

data class AllSocialResponse(
    @SerializedName("data") val dataList: MutableList<SocialData>?,
    @SerializedName("message") val message : String?,
    @SerializedName("status") val status : Int?

)