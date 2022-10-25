package com.andtv.flicknplay.workbrowse.data.remote.datasource

import com.andtv.flicknplay.workbrowse.data.remote.api.AccountSettingsApi
import javax.inject.Inject

class AccountSettingsDataSource @Inject constructor(
    private val accountSettingsApi: AccountSettingsApi
) {

    fun getLanguagesCountiesTimeZones() = accountSettingsApi.getLanguagesCountiesTimeZones()

    fun updateCountryTimeZone(){

    }

    fun updatePassword(){
    }

    fun deleteAccount(){

    }
}