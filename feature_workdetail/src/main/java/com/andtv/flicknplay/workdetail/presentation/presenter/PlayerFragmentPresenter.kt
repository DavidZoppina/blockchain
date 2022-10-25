package com.andtv.flicknplay.workdetail.presentation.presenter

import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.presentation.extension.addTo
import com.andtv.flicknplay.presentation.presenter.AutoDisposablePresenter
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.workdetail.domain.GetSeasonEpisodesUseCase
import com.andtv.flicknplay.workdetail.domain.UpdateVideoHistoryUseCase
import com.andtv.flicknplay.workdetail.domain.model.toWorkDetail
import com.andtv.flicknplay.workdetail.presentation.mapper.toViewModel
import com.andtv.flicknplay.workdetail.presentation.model.VideoViewModel
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class PlayerFragmentPresenter @Inject constructor(
    private val view: View,
    private val updateVideoHistoryUseCase: UpdateVideoHistoryUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase,
    private val rxScheduler: RxScheduler
    ) : AutoDisposablePresenter() {


    fun loadSeasonEpisodes(movideId: Int, seasonNumber: Int){
        getSeasonEpisodesUseCase(movideId, seasonNumber)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .map {movieDetail->
                movieDetail.videos?.toWorkDetail().also {
                    it?.forEach { it.thumbnail = movieDetail.backdropPath }
                }}
            .map { it.toViewModel() }
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.showProgress() }
            .doFinally { view.hideProgress() }
            .subscribe({ result ->
                view.onEpisodesLoaded(result)
            }, { throwable ->
                Timber.e(throwable, "Error while loading episodes by work")
                view.onEpisodesLoadingFailed()
            }).addTo(compositeDisposable)
    }

    fun updateVideoHistory(movieId: Int, seekTime: Int, duration:Int){
        updateVideoHistoryUseCase(movieId, seekTime, duration)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .subscribe({
                view.historyUpdated()
            }, { throwable ->
                Timber.e(throwable, "Error while settings the work as favorite")
                view.videoUpdateFailed()
            }).addTo(compositeDisposable)
    }

    interface View{
        fun historyUpdated()
        fun videoUpdateFailed()

        fun onEpisodesLoaded(result: List<VideoViewModel>)
        fun onEpisodesLoadingFailed()
        fun showProgress()
        fun hideProgress()
    }
}