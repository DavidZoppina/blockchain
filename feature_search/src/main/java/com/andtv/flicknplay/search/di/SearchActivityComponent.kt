

package com.andtv.flicknplay.search.di

import com.andtv.flicknplay.presentation.di.annotation.ActivityScope
import dagger.Subcomponent

/**
 *
 */
@ActivityScope
@Subcomponent
interface SearchActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchActivityComponent
    }

    fun searchFragmentComponent(): SearchFragmentComponent.Factory
}
