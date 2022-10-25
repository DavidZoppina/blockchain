package com.andtv.flicknplay.workbrowse.domain

import com.andtv.flicknplay.workbrowse.data.repository.AccountSettingRepository
import javax.inject.Inject

class GetLanguageCountryTimeZoneUseCase @Inject constructor (
    private val accountSettingRepository: AccountSettingRepository
        ) {

    operator fun invoke()= accountSettingRepository.getLanguagesCountiesTimeZones()
}