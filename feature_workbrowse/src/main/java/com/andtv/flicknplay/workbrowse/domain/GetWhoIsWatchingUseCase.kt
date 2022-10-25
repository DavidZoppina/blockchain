package com.andtv.flicknplay.workbrowse.domain

import com.andtv.flicknplay.workbrowse.data.repository.WhoIsWatchingRepository
import javax.inject.Inject

class GetWhoIsWatchingUseCase @Inject constructor(
    private val whoIsWatchingRepository: WhoIsWatchingRepository
){

    operator fun invoke (userId: Int) = whoIsWatchingRepository.getAllAccountUsers(userId)
}