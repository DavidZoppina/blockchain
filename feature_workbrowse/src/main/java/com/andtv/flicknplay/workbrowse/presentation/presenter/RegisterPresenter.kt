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

package com.andtv.flicknplay.workbrowse.presentation.presenter

import android.util.Log
import com.google.gson.Gson
import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.presentation.extension.addTo
import com.andtv.flicknplay.presentation.presenter.AutoDisposablePresenter
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.workbrowse.data.remote.model.register.RegisterRequestModel
import com.andtv.flicknplay.workbrowse.data.remote.model.register.RegisterResponse
import com.andtv.flicknplay.workbrowse.domain.GetRegisterUseCase
import timber.log.Timber
import javax.inject.Inject
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.andtv.flicknplay.workbrowse.data.remote.model.ErrorResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.remote.model.login.LoginResponse
import com.andtv.flicknplay.workbrowse.domain.GetGoogleLoginUseCase
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import okhttp3.ResponseBody

@FragmentScope
class RegisterPresenter @Inject constructor(
    private val view: View,
    private val registerUseCase: GetRegisterUseCase,
    private val googleLogin : GetGoogleLoginUseCase,
    private val rxScheduler: RxScheduler
) : AutoDisposablePresenter() {


    fun register(request: RegisterRequestModel) {

        registerUseCase(request)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally { view.onHideProgress() }
            .subscribe({ workPage ->
                workPage?.let {
                    view.onRegistered(it)
                }
            }, { throwable ->
                if (throwable is HttpException && throwable.response().errorBody() is ResponseBody) {
                    val errorBody: String = (throwable.response().errorBody() as ResponseBody).string()

                    view.onErrorWorksLoaded(
                        Gson().fromJson(
                            errorBody,
                            ErrorResponse::class.java
                        )
                    )
                }
                Timber.e(throwable, "Error while loading the works by genre")

            }).addTo(compositeDisposable)
    }

    fun googleRegister(provider:String,action:String,request: SocialLoginModel) {
        googleLogin(provider,action,request)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally {
                view.onHideProgress()
            }
            .subscribe({ workPage ->
                workPage?.let {
                    view.onGoogleRegisterd(it)
                }
            }, { throwable ->
                if (throwable is HttpException && throwable.response().errorBody() is ResponseBody) {
                    val errorBody: String = (throwable.response().errorBody() as ResponseBody).string()
                    view.onErrorWorksLoaded(
                        Gson().fromJson(
                            errorBody,
                            ErrorResponse::class.java
                        )
                    )
                }
                Timber.e(throwable, "Error while loading the works by genre")

            }).addTo(compositeDisposable)

    }

    fun twitterRegister(request: RegisterRequestModel) {
        registerUseCase(request)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally { view.onHideProgress() }
            .subscribe({ workPage ->
                workPage?.let {
                    view.onTwitterRegistered(it)
                }
            }, { throwable ->
                if (throwable is HttpException && throwable.response().errorBody() is ResponseBody) {
                    val errorBody: String = (throwable.response().errorBody() as ResponseBody).string()

                    view.onErrorWorksLoaded(
                        Gson().fromJson(
                            errorBody,
                            ErrorResponse::class.java
                        )
                    )
                }
                Timber.e(throwable, "Error while loading the works by genre")

            }).addTo(compositeDisposable)

    }




    interface View {
        fun onRegistered(registerResponse: RegisterResponse)
        fun onTwitterRegistered(registerResponse: RegisterResponse)
        fun onShowProgress()
        fun onHideProgress()
        fun onErrorWorksLoaded(errorResponse: ErrorResponse)
        fun onGoogleRegisterd(googleRegisterResponse : LoginResponse)
    }
}
