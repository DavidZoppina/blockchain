package com.andtv.flicknplay.workbrowse.presentation.presenter

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.presentation.extension.addTo
import com.andtv.flicknplay.presentation.presenter.AutoDisposablePresenter
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.workbrowse.domain.GetWhoIsWatchingUseCase
import com.andtv.flicknplay.workbrowse.domain.model.toViewModel
import com.andtv.flicknplay.workbrowse.presentation.model.WhoIsWatchingUserViewModel
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class WhoIsWatchingPresenter @Inject constructor(
    private val view: View,
    private val whoIsWatchingUseCase: GetWhoIsWatchingUseCase,
    private val rxScheduler: RxScheduler
): AutoDisposablePresenter() {

    fun getAllAccountUser(userId: Int){
     whoIsWatchingUseCase(userId)
         .subscribeOn(rxScheduler.ioScheduler)
         .observeOn(rxScheduler.mainScheduler)
         .map { profiles->
             profiles.toViewModel().also {
                 it.forEach { item->
                     val lastIndex = item.avatar?.lastIndexOf('/') as Int
                     val avatar =
                         item.avatar?.substring((lastIndex + 1), item.avatar?.length!!)?.split(".")
                             ?.get(0)
                     item.avatar = avatar
                 }
             }
         }
         .subscribe({ result->
             view.onProfilesLoaded(result)
         }, { throwable ->
             Timber.e(throwable, "Error while settings the work as favorite")
         }).addTo(compositeDisposable)
    }

    interface View{
        fun onProfilesLoaded(profiles:List<WhoIsWatchingUserViewModel>)
    }
}