

package com.andtv.flicknplay.workbrowse.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 *
 */
abstract class GenreListResponse<T : GenreResponse>(
    @SerializedName("genres") var genres: List<T>? = null
)
