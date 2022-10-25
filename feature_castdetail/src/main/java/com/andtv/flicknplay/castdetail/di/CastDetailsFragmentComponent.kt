

package com.andtv.flicknplay.castdetail.di

import com.andtv.flicknplay.castdetail.di.module.CastRemoteDataSourceModule
import com.andtv.flicknplay.castdetail.presentation.presenter.CastDetailsPresenter
import com.andtv.flicknplay.castdetail.presentation.ui.fragment.CastDetailsFragment
import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import dagger.BindsInstance
import dagger.Subcomponent

/**
 *
 */
@FragmentScope
@Subcomponent(
        modules = [
            CastRemoteDataSourceModule::class
        ]
)
interface CastDetailsFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance view: CastDetailsPresenter.View): CastDetailsFragmentComponent
    }

    fun inject(castDetailsFragment: CastDetailsFragment)
}
