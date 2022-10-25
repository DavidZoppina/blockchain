package com.andtv.flicknplay.workdetail.di

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workdetail.di.module.MovieRemoteDataSourceModule
import com.andtv.flicknplay.workdetail.presentation.presenter.PlayerFragmentPresenter
import com.andtv.flicknplay.workdetail.presentation.ui.fragment.PlayerFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(
    modules = [
        MovieRemoteDataSourceModule::class
    ]
)
interface PlayerFragmentComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance view:PlayerFragmentPresenter.View
        ): PlayerFragmentComponent
    }

    fun inject (playerFragment: PlayerFragment)


}