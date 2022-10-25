package com.andtv.flicknplay.workbrowse.di


import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.di.module.WhoIsWatchingApiModule
import com.andtv.flicknplay.workbrowse.presentation.presenter.ProfileLoginPresenter

import com.andtv.flicknplay.workbrowse.presentation.ui.fragment.ProfileLoginFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        WhoIsWatchingApiModule::class
    ]
)
interface ProfileLoginFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance view: ProfileLoginPresenter.View): ProfileLoginFragmentComponent
    }

    fun inject(profileLoginFragment: ProfileLoginFragment)
}