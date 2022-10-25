package com.andtv.flicknplay.workbrowse.domain

import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.repository.LoginRepository
import javax.inject.Inject

class GetGoogleLoginUseCase @Inject constructor(

    private val loginRepository: LoginRepository
) {

    operator fun invoke(provider:String,action:String,request: SocialLoginModel) = loginRepository.socialLogin(provider,action,request)
}