package com.andtv.flicknplay.workbrowse.domain

import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.AccountDetailRequest
import com.andtv.flicknplay.workbrowse.data.repository.AccountSettingDetailRepository
import com.andtv.flicknplay.workbrowse.data.repository.LoginRepository
import javax.inject.Inject

class GetAllSocialUseCase @Inject constructor(
    private val accountSettingDetailRepository: AccountSettingDetailRepository
) {
    operator fun invoke(request: SocialLoginModel) = accountSettingDetailRepository.getAllSocialProfile(request)
}