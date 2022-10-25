package com.andtv.flicknplay.workbrowse.presentation.model

import java.io.Serializable

data class WhoIsWatchingUserViewModel(
    val id: Int? = null,
    val userId: Int? = null,
    val name: String? = null,
    var avatar: String? = null,
    val kids: Any? = null,
    val link: String? = null,
    val master: Int? = null,
    val isSet: Int? = null,
    val rating: String? = null,
) : Serializable
