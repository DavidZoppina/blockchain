package com.andtv.flicknplay.model.data.remote

import com.google.gson.annotations.SerializedName

data class VideoHistoryModel(
    @SerializedName("history") var history: List<FlicknplayHistoryMovieResponse> = arrayListOf()
)
