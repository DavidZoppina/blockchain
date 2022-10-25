package com.andtv.flicknplay.workbrowse.di

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.di.module.WhoIsWatchingApiModule
import com.andtv.flicknplay.workbrowse.presentation.presenter.CreateUserProfilePresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.fragment.CreateUserProfileFragment
import dagger.BindsInstance
import dagger.Subcomponent


@FragmentScope
@Subcomponent(
    modules = [
        WhoIsWatchingApiModule::class
    ]
)

interface CreateUserFragmentComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance view: CreateUserProfilePresenter.View): CreateUserFragmentComponent
    }

    fun inject (createUserProfileFragment: CreateUserProfileFragment)
}