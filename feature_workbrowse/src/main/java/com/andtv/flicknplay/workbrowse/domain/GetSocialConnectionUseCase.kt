package com.andtv.flicknplay.workbrowse.domain

import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.repository.AccountSettingDetailRepository
import com.andtv.flicknplay.workbrowse.data.repository.LoginRepository
import javax.inject.Inject

class GetSocialConnectionUseCase @Inject constructor(
    private val accountSettingDetailRepository: AccountSettingDetailRepository
) {

    operator fun invoke(provider:String,action:String,request: SocialLoginModel) = accountSettingDetailRepository.socialConnection(provider,action,request)
}