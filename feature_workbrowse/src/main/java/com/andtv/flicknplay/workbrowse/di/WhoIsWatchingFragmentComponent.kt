package com.andtv.flicknplay.workbrowse.di

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.di.module.WhoIsWatchingApiModule
import com.andtv.flicknplay.workbrowse.presentation.presenter.WhoIsWatchingPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.fragment.WhoIsWatchingFragment
import dagger.BindsInstance
import dagger.Subcomponent


@FragmentScope
@Subcomponent(
    modules = [WhoIsWatchingApiModule::class]
)

interface WhoIsWatchingFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create (@BindsInstance view: WhoIsWatchingPresenter.View): WhoIsWatchingFragmentComponent
    }

    fun inject (whoIsWatchingFragment: WhoIsWatchingFragment)

}