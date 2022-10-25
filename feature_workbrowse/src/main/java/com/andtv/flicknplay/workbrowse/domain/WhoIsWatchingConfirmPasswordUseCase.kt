package com.andtv.flicknplay.workbrowse.domain

import com.andtv.flicknplay.workbrowse.data.repository.WhoIsWatchingRepository
import javax.inject.Inject

class WhoIsWatchingConfirmPasswordUseCase @Inject constructor(
    private val  whoIsWatchingRepository: WhoIsWatchingRepository
){

    operator fun invoke(id: String, password: String) = whoIsWatchingRepository.confirmPassword(id, password)
}