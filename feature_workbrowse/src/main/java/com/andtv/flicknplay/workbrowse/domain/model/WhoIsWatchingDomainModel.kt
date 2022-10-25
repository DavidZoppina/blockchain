package com.andtv.flicknplay.workbrowse.domain.model

import com.andtv.flicknplay.workbrowse.presentation.model.WhoIsWatchingUserViewModel

data class WhoIsWatchingDomainModel(
    val id: Int? = null,
    val userId: Int? = null,
    val name: String? = null,
    var avatar: String? = null,
    val kids: Any? = null,
    val link: String? = null,
    val password: String? = null,
    val master: Int? = null,
    val isSet: Int? = null,
    val rating: String? = null,
)

fun WhoIsWatchingDomainModel.toViewModel() = WhoIsWatchingUserViewModel(id, userId, name, avatar, kids, link, master, isSet, rating)
fun List<WhoIsWatchingDomainModel>.toViewModel() = map { it.toViewModel() }