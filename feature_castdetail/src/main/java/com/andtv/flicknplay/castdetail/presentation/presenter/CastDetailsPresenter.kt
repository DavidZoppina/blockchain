

package com.andtv.flicknplay.castdetail.presentation.presenter

import androidx.leanback.widget.Presenter
import com.andtv.flicknplay.castdetail.domain.GetCastDetailsUseCase
import com.andtv.flicknplay.model.presentation.mapper.toViewModel
import com.andtv.flicknplay.model.presentation.model.CastViewModel
import com.andtv.flicknplay.model.presentation.model.WorkViewModel
import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.presentation.extension.addTo
import com.andtv.flicknplay.presentation.presenter.AutoDisposablePresenter
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.route.Route
import com.andtv.flicknplay.route.workdetail.WorkDetailsRoute
import javax.inject.Inject
import timber.log.Timber

/**
 *
 */
@FragmentScope
class CastDetailsPresenter @Inject constructor(
    private val view: View,
    private val getCastDetailsUseCase: GetCastDetailsUseCase,
    private val workDetailsRoute: WorkDetailsRoute,
    private val rxScheduler: RxScheduler
) : AutoDisposablePresenter() {

    fun loadCastDetails(castViewModel: CastViewModel) {
        getCastDetailsUseCase(castViewModel.id)
                .subscribeOn(rxScheduler.ioScheduler)
                .observeOn(rxScheduler.computationScheduler)
                .map { result ->
                    val cast = result.first.toViewModel()
                    val movies = result.second?.filter{ it.isSeries == false }?.map { it.toViewModel() }
                    val tvShow = result.second?.filter{ it.isSeries == true }?.map { it.toViewModel() }

                    Triple(cast, movies, tvShow)
                }
                .observeOn(rxScheduler.mainScheduler)
                .doOnSubscribe { view.onShowProgress() }
                .doFinally { view.onHideProgress() }
                .subscribe({ result ->
                    val cast = result.first
                    val movies = result.second
                    val tvShow = result.third

                    view.onCastLoaded(cast, movies, tvShow)
                }, { throwable ->
                    Timber.e(throwable, "Error while getting the cast details")
                    view.onErrorCastDetailsLoaded()
                }).addTo(compositeDisposable)
    }

    fun workClicked(itemViewHolder: Presenter.ViewHolder, workViewModel: WorkViewModel) {
        val route = workDetailsRoute.buildWorkDetailRoute(workViewModel)
        view.openWorkDetails(itemViewHolder, route)
    }

    interface View {

        fun onShowProgress()

        fun onHideProgress()

        fun onCastLoaded(
            castViewModel: CastViewModel?,
            movies: List<WorkViewModel>?,
            tvShow: List<WorkViewModel>?
        )

        fun onErrorCastDetailsLoaded()

        fun openWorkDetails(itemViewHolder: Presenter.ViewHolder, route: Route)
    }
}
