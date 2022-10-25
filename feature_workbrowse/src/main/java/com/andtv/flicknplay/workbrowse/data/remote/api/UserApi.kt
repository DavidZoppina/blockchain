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

package com.andtv.flicknplay.workbrowse.data.remote.api

import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.login.LoginRequestModel
import com.andtv.flicknplay.workbrowse.data.remote.model.login.LoginResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.register.RegisterRequestModel
import com.andtv.flicknplay.workbrowse.data.remote.model.register.RegisterResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *
 */
interface UserApi {
    @POST("auth/login")
    fun token(@Body request: LoginRequestModel): Single<LoginResponse>
    @POST("auth/register")
    fun register(@Body request: RegisterRequestModel): Single<RegisterResponse>
    @POST("auth/social/{provider}/{action}")
    fun socialLogin(@Path("provider") provider:String,@Path("action") action:String, @Body request : SocialLoginModel) : Single<LoginResponse>



    companion object{

        val GOOGLE_PROVIDER="google"
        val FACEBOOK_PROVIDER="facebook"
        val TWITTER_PROVIDER="twitter"

        val REGISTER_SOCIAL_ACCOUNT="social-login-api"
        val CONNECT_SOCIAL_ACCOUNT="connect-social-account"
        val DISCONNECT_SOCIAL_ACCOUNT="disconnect-social-account"

    }
}
