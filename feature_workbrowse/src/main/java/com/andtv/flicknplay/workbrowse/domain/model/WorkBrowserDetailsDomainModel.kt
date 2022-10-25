package com.andtv.flicknplay.workbrowse.domain.model

import com.andtv.flicknplay.model.domain.PageDomainModel
import com.andtv.flicknplay.model.domain.WorkDomainModel
import com.andtv.flicknplay.workbrowse.presentation.mapper.toViewModel
import com.andtv.flicknplay.workbrowse.presentation.model.WorkBrowserDetailsViewModel

data class WorkBrowserDetailsDomainModel(val hasFavoriteMovie: Boolean,
                                         val movieGenreList: List<GenreDomainModel>?,
                                         val tvShowGenreList: List<GenreDomainModel>?,
                                         val continueWatchingMovies: PageDomainModel<WorkDomainModel>?)


fun WorkBrowserDetailsDomainModel.toViewModel() = WorkBrowserDetailsViewModel(
    hasFavoriteMovie,
    movieGenreList?.map { it.toViewModel() },
    tvShowGenreList?.map { it.toViewModel() },
    continueWatchingMovies

)