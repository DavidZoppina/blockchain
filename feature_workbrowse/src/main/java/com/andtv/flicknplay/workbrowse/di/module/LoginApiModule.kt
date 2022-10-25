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

package com.andtv.flicknplay.workbrowse.di.module

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.workbrowse.data.remote.api.UserApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Copyright (C) Flicknplay 20-10-2019.
 */
@Module
class LoginApiModule {

    @Provides
    @FragmentScope

    fun provideLoginApi(@Named("flickbaseurl") retrofit: Retrofit) =
        retrofit.create(UserApi::class.java)
}
