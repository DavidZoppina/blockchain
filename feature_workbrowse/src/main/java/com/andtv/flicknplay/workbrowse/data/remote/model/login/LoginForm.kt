package com.andtv.flicknplay.workbrowse.data.remote.model.login

import androidx.annotation.Keep

@Keep
data class LoginForm(
        val username: String? =null,
        val password: String? =null,
        val rememberMe: Boolean=false

)