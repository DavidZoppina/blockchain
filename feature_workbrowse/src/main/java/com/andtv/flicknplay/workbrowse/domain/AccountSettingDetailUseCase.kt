package com.andtv.flicknplay.workbrowse.domain

import com.andtv.flicknplay.workbrowse.data.repository.AccountSettingDetailRepository
import com.andtv.flicknplay.workbrowse.data.repository.WhoIsWatchingRepository
import javax.inject.Inject

class AccountSettingDetailUseCase @Inject constructor(
    private val accountSettingDetailRepository: AccountSettingDetailRepository
) {
    operator fun invoke (id:Int) = accountSettingDetailRepository.getAccountSettingDetail(id)
}