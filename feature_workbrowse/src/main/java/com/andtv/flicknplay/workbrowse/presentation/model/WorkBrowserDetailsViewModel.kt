package com.andtv.flicknplay.workbrowse.presentation.model

import com.andtv.flicknplay.model.domain.PageDomainModel
import com.andtv.flicknplay.model.domain.WorkDomainModel

data class WorkBrowserDetailsViewModel(val hasFavoriteMovie: Boolean,
                                       val movieGenreList: List<GenreViewModel>?,
                                       val tvShowGenreList: List<GenreViewModel>?,
                                       val continueWatchingMovies: PageDomainModel<WorkDomainModel>?)
