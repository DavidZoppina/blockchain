package com.andtv.flicknplay.workbrowse.presentation.presenter

import com.andtv.flicknplay.workbrowse.R
import javax.inject.Inject


private const val EDIT_PROFILE = 1L
private const val CHANGE_PROFILE = 2L
private val GUIDED_ACTIONS = listOf(
    EDIT_PROFILE to R.string.edit_profile,
    CHANGE_PROFILE to R.string.change_profile
)
class SettingPresenter @Inject constructor(
    private val view:View
){

    fun getGuidedActions() =
        GUIDED_ACTIONS

    fun guidedActionClicked (id: Long)
    {
        when(id) {
            EDIT_PROFILE -> view.onEditProfileClicked()
            CHANGE_PROFILE -> view.onChangeProfileClicked()
        }
    }

    interface View {
        fun onEditProfileClicked ()
        fun onChangeProfileClicked ()
    }
}