package com.andtv.flicknplay.workbrowse.data.remote.model

import com.google.gson.annotations.SerializedName

data class Errors (

	@SerializedName("email") val email : List<String>
)