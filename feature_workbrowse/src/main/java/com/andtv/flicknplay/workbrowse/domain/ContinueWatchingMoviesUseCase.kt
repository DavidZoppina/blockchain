package com.andtv.flicknplay.workbrowse.domain

import com.andtv.flicknplay.workbrowse.data.repository.MovieRepository
import javax.inject.Inject

class ContinueWatchingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke() = movieRepository.getContinueWatchingMovies()

}