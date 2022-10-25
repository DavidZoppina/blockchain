package com.andtv.flicknplay.workbrowse.di

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.presentation.presenter.AccountSettingPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.fragment.AccountSettingFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent
//    (
//    modules = [AccountSettingsApiModule::class]
//)
interface AccountSettingFragmentComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance view: AccountSettingPresenter.View): AccountSettingFragmentComponent
    }

    fun inject (accountSettingFragment: AccountSettingFragment)
}