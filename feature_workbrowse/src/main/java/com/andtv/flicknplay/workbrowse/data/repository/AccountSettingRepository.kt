package com.andtv.flicknplay.workbrowse.data.repository

import com.andtv.flicknplay.workbrowse.data.remote.datasource.AccountSettingsDataSource
import javax.inject.Inject

class AccountSettingRepository @Inject constructor(
    private val accountSettingsDataSource: AccountSettingsDataSource
){

    fun getLanguagesCountiesTimeZones() = accountSettingsDataSource.getLanguagesCountiesTimeZones()
}