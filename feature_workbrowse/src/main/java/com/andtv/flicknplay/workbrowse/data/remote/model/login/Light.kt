package com.andtv.flicknplay.workbrowse.data.remote.model.login
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class Light (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("is_dark") val is_dark : Boolean,
    @SerializedName("default_light") val default_light : Boolean,
    @SerializedName("default_dark") val default_dark : Boolean,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("colors") val colors : Colors,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)