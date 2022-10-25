package com.andtv.flicknplay.workbrowse.data.remote.model.login
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Themes (

    @SerializedName("dark") val dark : Dark,
    @SerializedName("light") val light : Light,
    @SerializedName("selected") val selected : String
)