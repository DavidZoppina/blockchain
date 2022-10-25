

package com.andtv.flicknplay.search.di

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.search.di.module.FlicknplayRemoteDataSourceModule
import com.andtv.flicknplay.search.presentation.presenter.SearchPresenter
import com.andtv.flicknplay.search.presentation.ui.fragment.SearchFragment
import dagger.BindsInstance
import dagger.Subcomponent

/**
 *
 */
@FragmentScope
@Subcomponent(
        modules = [
            FlicknplayRemoteDataSourceModule::class
        ]
)
interface SearchFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance view: SearchPresenter.View): SearchFragmentComponent
    }

    fun inject(searchFragment: SearchFragment)
}
