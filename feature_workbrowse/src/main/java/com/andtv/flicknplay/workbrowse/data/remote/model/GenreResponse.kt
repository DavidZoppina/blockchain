

package com.andtv.flicknplay.workbrowse.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 *
 */
abstract class GenreResponse(
    @SerializedName("id") open var id: Int = 0,
    @SerializedName("name") open var name: String? = null
) {

    abstract val source: Source

    enum class Source {
        MOVIE,
        TV_SHOW
    }
}
