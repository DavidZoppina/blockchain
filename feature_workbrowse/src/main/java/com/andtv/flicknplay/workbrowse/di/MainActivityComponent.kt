/*
 * Copyright (C) 2021 Flicknplay
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.andtv.flicknplay.workbrowse.di

import com.andtv.flicknplay.presentation.di.annotation.ActivityScope
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import dagger.Subcomponent

/**
 * Copyright (C) Flicknplay 2019-08-29.
 */
@ActivityScope
@Subcomponent
interface MainActivityComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivityComponent
    }

    fun inject(activity: MainActivity)

    fun aboutFragmentComponent(): AboutFragmentComponent.Factory

    fun accountFragmentComponent(): AccountFragmentComponent.Factory

    fun accountSettingFragmentComponent(): AccountSettingFragmentComponent.Factory

    fun settingFragmentComponent(): SettingFragmentComponent.Factory

    fun genreWorkGridFragmentComponent(): GenreWorkGridFragmentComponent.Factory

    fun topWorkGridFragmentComponent(): TopWorkGridFragmentComponent.Factory

    fun workBrowseFragmentComponent(): WorkBrowseFragmentComponent.Factory

    fun loginFragmentComponent(): LoginFragmentComponent.Factory

    fun registerFragmentComponent(): RegisterFragmentComponent.Factory

    fun profileLoginFragmentComponent() :ProfileLoginFragmentComponent.Factory

    fun whoIsWatchingFragmentComponent(): WhoIsWatchingFragmentComponent.Factory

    fun createUserProfileFragmentComponent(): CreateUserFragmentComponent.Factory

    fun accountSettingDetailFragmentComponent() :AccountSettingDetailFragmentComponent.Factory

}
