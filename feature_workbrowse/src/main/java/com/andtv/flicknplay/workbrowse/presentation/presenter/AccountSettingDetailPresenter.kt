package com.andtv.flicknplay.workbrowse.presentation.presenter

import android.util.Log
import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.presentation.extension.addTo
import com.andtv.flicknplay.presentation.presenter.AutoDisposablePresenter
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.workbrowse.data.remote.model.AllSocialResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.ErrorResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.AccountDetailRequest
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.ResetPasswordRequest
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.User
import com.andtv.flicknplay.workbrowse.data.remote.model.login.LoginResponse
import com.andtv.flicknplay.workbrowse.domain.*
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import okhttp3.ResponseBody
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class AccountSettingDetailPresenter@Inject constructor(
    private val accountSettingDetailUseCase: AccountSettingDetailUseCase,
    private val view: AccountSettingDetailPresenter.View,
    private val getSocialConnection : GetSocialConnectionUseCase,
    private val getUpdateAccountDetailUseCase: GetUpdateAccountDetailUseCase,
    private val getAllSocialUseCase: GetAllSocialUseCase,
    private val getUpdatePasswordUseCase:GetUpdatePasswordUseCase,
    private val rxScheduler: RxScheduler
): AutoDisposablePresenter(){

    fun getAccountSettingDetail(id:Int) {
        accountSettingDetailUseCase(id)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally { view.onHideProgress() }
            .subscribe({ result->
                Timber.i(Gson().toJson(result))
                if(result.status == "success")
                    view.onGetAccountSettingDetail(result.user)
                else
                    view.onGetAccountSettingDetailFailed()

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

    fun socialConnection(provider:String,action:String,request: SocialLoginModel) {
        Timber.i( Gson().toJson(request))
        getSocialConnection(provider,action,request)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally {
                view.onHideProgress()
            }
            .subscribe({ workPage ->
                workPage?.let {
                    Timber.i( Gson().toJson(it))
                    view.onSocialAccountResponse(it)
                }
            }, { throwable ->
                view.onError("Email miss matched, Make sure its your account")
                if (throwable is HttpException && throwable.response().errorBody() is ResponseBody) {
                    Timber.e(throwable,throwable.response().body().toString())
                    val errorBody: String = (throwable.response().errorBody() as ResponseBody).string()
                    Timber.e(throwable,errorBody)
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

    fun updateUserProfile(id:String,request: AccountDetailRequest) {
        Timber.i( Gson().toJson(request))
        getUpdateAccountDetailUseCase(id,request)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally {
                view.onHideProgress()
            }
            .subscribe({ workPage ->
                workPage?.let {
                    Timber.i( Gson().toJson(it))
                    view.onAccountUpdate(it)
                }
            }, { throwable ->
                if (throwable is HttpException && throwable.response().errorBody() is ResponseBody) {
                    Timber.e(throwable,throwable.response().body().toString())
                    val errorBody: String = (throwable.response().errorBody() as ResponseBody).string()
                    Timber.e(throwable,errorBody)
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

    fun updatePassword(request: ResetPasswordRequest) {
        Timber.i( Gson().toJson(request))
        getUpdatePasswordUseCase(request)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally {
                view.onHideProgress()
            }
            .subscribe({ workPage ->
                workPage?.let {
                    Timber.i( Gson().toJson(it))
                    view.onPasswordUpdate(it)
                }
            }, { throwable ->
                if (throwable is HttpException && throwable.response().errorBody() is ResponseBody) {
                    Timber.e(throwable,throwable.response().body().toString())
                    val errorBody: String = (throwable.response().errorBody() as ResponseBody).string()
                    Timber.e(throwable,errorBody)
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


    fun getAllSocialProfile(request: SocialLoginModel) {
        Timber.i( Gson().toJson(request))
        getAllSocialUseCase(request)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally {
                view.onHideProgress()
            }
            .subscribe({ workPage ->
                workPage?.let {
                    Timber.i( Gson().toJson(it))
                    view.onAllSocialAccount(it)
                }
            }, { throwable ->
                if (throwable is HttpException && throwable.response().errorBody() is ResponseBody) {
                    Timber.e(throwable,throwable.response().body().toString())
                    val errorBody: String = (throwable.response().errorBody() as ResponseBody).string()
                    Timber.e(throwable,errorBody)
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
        fun onPasswordUpdate(socialResponse : SocialResponse)
        fun onAllSocialAccount(allSocialResponse: AllSocialResponse)
        fun onAccountUpdate(socialResponse : SocialResponse)
        fun onSocialAccountResponse(socialResponse : AllSocialResponse)
        fun onGetAccountSettingDetail(user:User)
        fun onGetAccountSettingDetailFailed()
        fun onShowProgress()
        fun onHideProgress()
        fun onError(message:String)
        fun onErrorWorksLoaded(errorResponse: ErrorResponse)
    }

}