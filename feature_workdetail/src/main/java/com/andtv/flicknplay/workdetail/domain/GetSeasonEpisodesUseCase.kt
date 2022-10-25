package com.andtv.flicknplay.workdetail.domain

import com.andtv.flicknplay.workdetail.data.repository.MovieRepository
import javax.inject.Inject

class GetSeasonEpisodesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(movieId: Int, seasonNumber: Int) =
        movieRepository.getEpisodesByMovieIDAndSeasonID(movieId, seasonNumber)
}