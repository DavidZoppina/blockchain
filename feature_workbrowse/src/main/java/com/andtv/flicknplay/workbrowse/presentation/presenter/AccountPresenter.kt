package com.andtv.flicknplay.workbrowse.presentation.presenter

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.R
import javax.inject.Inject


private const val ACCOUNT_ID = 1L
private val GUIDED_ACTIONS = listOf(
    ACCOUNT_ID to R.string.setting
)

@FragmentScope
class AccountPresenter @Inject constructor(private val view: View){

    fun getGuidedActions() =
        GUIDED_ACTIONS



    fun guidedActionClicked (id: Long)
    {
        when(id) {
            ACCOUNT_ID -> view.onAccountClicked()
        }
    }

    interface View {

        fun onAccountClicked()
    }
}