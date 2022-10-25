package com.andtv.flicknplay.workbrowse.domain.model

import com.andtv.flicknplay.workbrowse.data.repository.LoginRepository
import javax.inject.Inject

class GetTwitterLoginUseCase @Inject constructor(

    private val loginRepository: LoginRepository
) {

//    operator fun invoke(request: TwitterRegisterModel) = loginRepository.twitterLogin(request)
}