package com.andtv.flicknplay.workbrowse.data.remote.model

data class UpdateUserProfileRequestModel(
    val id:String,
    val avatar: String,
    val kids: Boolean,
    val name: String,
    val password: String,
    val rating:String
    )
