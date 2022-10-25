package com.andtv.flicknplay.workbrowse.data.remote.model

import com.google.gson.annotations.SerializedName
import com.andtv.flicknplay.workbrowse.domain.model.WhoIsWatchingDomainModel

data class WhoIsWatchingUserResponse (
    @SerializedName("id") val id: Int? = null,
    @SerializedName("user_id") val userId: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("kids") val kids: Any? = null,
    @SerializedName("link") val link: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("master") val master: Int? = null,
    @SerializedName("is_set") val isSet: Int? = null,
    @SerializedName("rating") val rating: String? = null,
)

fun WhoIsWatchingUserResponse.toDomainModel() = WhoIsWatchingDomainModel(id, userId, name, avatar, kids, link, password, master, isSet, rating)

fun List<WhoIsWatchingUserResponse>.toDomainModel() = map { it.toDomainModel() }