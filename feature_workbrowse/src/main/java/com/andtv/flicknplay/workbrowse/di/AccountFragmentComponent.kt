package com.andtv.flicknplay.workbrowse.di

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.presentation.presenter.AccountPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.fragment.AccountFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface AccountFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance view: AccountPresenter.View): AccountFragmentComponent
    }

    fun inject(accountFragment: AccountFragment)
}