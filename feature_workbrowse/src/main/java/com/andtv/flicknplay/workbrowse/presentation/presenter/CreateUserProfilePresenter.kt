package com.andtv.flicknplay.workbrowse.presentation.presenter

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.presentation.extension.addTo
import com.andtv.flicknplay.presentation.presenter.AutoDisposablePresenter
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.workbrowse.data.remote.model.ErrorResponse
import com.andtv.flicknplay.workbrowse.domain.CreateUserProfileUseCase
import com.andtv.flicknplay.workbrowse.domain.UpdateUserProfileUseCase
import okhttp3.ResponseBody
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class CreateUserProfilePresenter @Inject constructor(
    private val createUserProfileUseCase: CreateUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val view: View,
    private val rxScheduler: RxScheduler
): AutoDisposablePresenter() {
    fun createUserProfile(avatar: String, kids: Boolean, name: String, password: String, rating:String) {
        createUserProfileUseCase(avatar, kids, name, password, rating)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally { view.onHideProgress() }
            .subscribe({ result->
                if(result.success == "success")
                    view.onProfileCreated()
                else
                    view.onProfileCreationFailed()

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

    fun updateUserProfile(id: String, avatar: String, kids: Boolean, name: String, password: String, rating:String) {
        updateUserProfileUseCase(id, avatar, kids, name, password, rating)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally { view.onHideProgress() }
            .subscribe({ result->
                if(result.success == "success")
                    view.onProfileCreated()
                else
                    view.onProfileCreationFailed()

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
        fun onProfileCreated()
        fun onProfileCreationFailed()
        fun onShowProgress()
        fun onHideProgress()
        fun onErrorWorksLoaded(errorResponse: ErrorResponse)
    }
}