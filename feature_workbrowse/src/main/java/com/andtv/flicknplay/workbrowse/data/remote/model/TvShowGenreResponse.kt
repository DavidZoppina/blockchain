

package com.andtv.flicknplay.workbrowse.data.remote.model

/**
 *
 */
class TvShowGenreResponse(
    id: Int = 0,
    name: String? = null,
    override val source: Source = Source.TV_SHOW
) : GenreResponse(id = id, name = name)
