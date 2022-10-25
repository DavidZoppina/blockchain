

package com.andtv.flicknplay.workbrowse.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 *
 */
abstract class TokenResponse(
    @SerializedName("token") open var token: String? = null
)
