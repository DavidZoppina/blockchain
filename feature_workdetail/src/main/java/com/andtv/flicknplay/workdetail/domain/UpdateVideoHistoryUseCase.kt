package com.andtv.flicknplay.workdetail.domain

import com.andtv.flicknplay.workdetail.data.repository.MovieRepository
import javax.inject.Inject

class UpdateVideoHistoryUseCase @Inject constructor(
    private val movieRepository: MovieRepository
){
    operator fun invoke(videoId:Int, seekTime: Int, duration:Int) =
        movieRepository.updateVideoHistory(videoId, seekTime, duration)
}