

package com.andtv.flicknplay.workbrowse.data.remote.model

/**
 *
 */
class MovieGenreResponse(
    id: Int = 0,
    name: String? = null,
    override val source: Source = Source.MOVIE
) : GenreResponse(id = id, name = name)
