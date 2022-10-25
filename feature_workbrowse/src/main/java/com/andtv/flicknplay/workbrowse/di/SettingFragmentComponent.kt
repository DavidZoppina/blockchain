package com.andtv.flicknplay.workbrowse.di

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.presentation.presenter.SettingPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.fragment.SettingFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface SettingFragmentComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance view: SettingPresenter.View): SettingFragmentComponent
    }

    fun inject(settingFragment: SettingFragment)
}