

package com.andtv.flicknplay.castdetail.di

import com.andtv.flicknplay.castdetail.presentation.ui.activity.CastDetailsActivity
import com.andtv.flicknplay.presentation.di.annotation.ActivityScope
import dagger.Subcomponent

/**
 *
 */
@ActivityScope
@Subcomponent
interface CastDetailsActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CastDetailsActivityComponent
    }

    fun inject(activity: CastDetailsActivity)

    fun castDetailsFragmentComponent(): CastDetailsFragmentComponent.Factory
}
