package com.andtv.flicknplay.workbrowse.presentation.presenter

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.presentation.extension.addTo
import com.andtv.flicknplay.presentation.presenter.AutoDisposablePresenter
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.workbrowse.data.remote.model.ErrorResponse
import com.andtv.flicknplay.workbrowse.domain.WhoIsWatchingConfirmPasswordUseCase
import okhttp3.ResponseBody
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class ProfileLoginPresenter @Inject constructor(
    private val whoIsWatchingConfirmPasswordUseCase: WhoIsWatchingConfirmPasswordUseCase,
    private val view: View,
    private val rxScheduler: RxScheduler
) : AutoDisposablePresenter(){


    fun profileLogin(id: String,password:String){
        whoIsWatchingConfirmPasswordUseCase(id, password)

            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.onShowProgress() }
            .doFinally { view.onHideProgress() }
            .subscribe({ result->
                view.onLogged()
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
        fun onLogged()
        fun onShowProgress()
        fun onHideProgress()
        fun onErrorWorksLoaded(errorResponse: ErrorResponse)
    }
}