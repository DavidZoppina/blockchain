package com.andtv.flicknplay.workdetail.di

import com.andtv.flicknplay.presentation.di.annotation.ActivityScope
import com.andtv.flicknplay.workdetail.presentation.ui.activity.PlayerActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface PlayerActivityComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): PlayerActivityComponent
    }

    fun inject(playerActivity: PlayerActivity)

    fun playerFragmentComponent(): PlayerFragmentComponent.Factory
}