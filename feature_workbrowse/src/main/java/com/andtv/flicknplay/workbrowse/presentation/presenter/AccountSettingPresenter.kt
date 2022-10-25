package com.andtv.flicknplay.workbrowse.presentation.presenter

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.R
import javax.inject.Inject

private const val  SETTINGS_ID = 1L

private const val SETTING_LINK = "https://www.flicknplay.com/account/settings"
private val GUIDED_ACTIONS = listOf(
    SETTINGS_ID to R.string.account_settings,
)


@FragmentScope
class AccountSettingPresenter @Inject constructor(
    private val view: View,
//    private val getLanguageCountryTimeZoneUseCase: GetLanguageCountryTimeZoneUseCase
){


    fun getGuidedActions() =
        GUIDED_ACTIONS

//   fun getLangCountryTimeZones() {
//       getLanguageCountryTimeZoneUseCase()
//   }
    fun guidedActionClicked(id: Long) {
        when (id) {
            SETTINGS_ID -> view.openLink(SETTING_LINK)
        }
    }
    interface View{
        fun openLink(link: String)

    }
}