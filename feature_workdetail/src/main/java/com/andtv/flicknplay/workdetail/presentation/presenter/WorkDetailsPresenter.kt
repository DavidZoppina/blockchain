package com.andtv.flicknplay.workdetail.presentation.presenter

import android.util.Log
import android.widget.Toast
import androidx.leanback.widget.Presenter
import com.andtv.flicknplay.model.presentation.mapper.toViewModel
import com.andtv.flicknplay.model.presentation.model.CastViewModel
import com.andtv.flicknplay.model.presentation.model.PageViewModel
import com.andtv.flicknplay.model.presentation.model.WorkViewModel
import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.presentation.extension.addTo
import com.andtv.flicknplay.presentation.presenter.AutoDisposablePresenter
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.route.Route
import com.andtv.flicknplay.route.castdetail.CastDetailsRoute
import com.andtv.flicknplay.route.workdetail.WorkDetailsRoute
import com.andtv.flicknplay.workdetail.domain.*
import com.andtv.flicknplay.workdetail.domain.model.toWorkDetail
import com.andtv.flicknplay.workdetail.presentation.mapper.toViewModel
import com.andtv.flicknplay.workdetail.presentation.model.ReviewViewModel
import com.andtv.flicknplay.workdetail.presentation.model.VideoViewModel
import javax.inject.Inject
import timber.log.Timber

/**
 *
 */
@FragmentScope
class WorkDetailsPresenter @Inject constructor(
    private val view: View,
    private val workViewModel: WorkViewModel,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val getRecommendationByWorkUseCase: GetRecommendationByWorkUseCase,
    private val getSimilarByWorkUseCase: GetSimilarByWorkUseCase,
    private val getReviewByWorkUseCase: GetReviewByWorkUseCase,
    private val getWorkDetailsUseCase: GetWorkDetailsUseCase,
    private val getSeasonEpisodesUseCase: GetSeasonEpisodesUseCase,
    private val workDetailsRoute: WorkDetailsRoute,
    private val castDetailsRoute: CastDetailsRoute,
    private val rxScheduler: RxScheduler,
) : AutoDisposablePresenter() {

    private var recommendedPage = 0
    private var totalRecommendedPage = 0
    private var similarPage = 0
    private var totalSimilarPage = 0
    private var reviewPage = 0
    private var totalReviewPage = 0
    private val recommendedWorks by lazy { mutableListOf<WorkViewModel>() }
    private val similarWorks by lazy { mutableListOf<WorkViewModel>() }
    private val reviews by lazy { mutableListOf<ReviewViewModel>() }

    fun setFavorite() {
        setFavoriteUseCase(workViewModel)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.mainScheduler)
            .subscribe({
                workViewModel.isFavorite = !workViewModel.isFavorite
                view.resultSetFavoriteMovie(workViewModel.isFavorite)
            }, { throwable ->
                Timber.e(throwable, "Error while settings the work as favorite")
                view.resultSetFavoriteMovie(false)
            }).addTo(compositeDisposable)
    }

    fun loadSeasonEpisodes(seasonNumber: Int){
        getSeasonEpisodesUseCase(workViewModel.id, seasonNumber)
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
                view.episodesLoaded(result)
            }, { throwable ->
                Timber.e(throwable, "Error while loading episodes by work")
                view.errorWorkDetailsLoaded()
            }).addTo(compositeDisposable)
    }

    fun loadData() {
        getWorkDetailsUseCase(workViewModel)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .map { it.toViewModel() }
            .observeOn(rxScheduler.mainScheduler)
            .doOnSubscribe { view.showProgress() }
            .doFinally { view.hideProgress() }
            .subscribe({ result ->
                workViewModel.isFavorite = result.isFavorite
                with(result.recommended) {
                    recommendedPage = page
                    totalRecommendedPage = totalPages
                    results?.let {
                        recommendedWorks.addAll(it)
                    }
                }
                with(result.similar) {
                    similarPage = page
                    totalSimilarPage = totalPages
                    results?.let {
                        similarWorks.addAll(it)
                    }
                }
                with(result.reviews) {
                    reviewPage = page
                    totalReviewPage = totalPages
                    results?.let {
                        reviews.addAll(it)
                    }
                }

                view.dataLoaded(
                    workViewModel.isFavorite,
                    reviews,
                    result.videos,
                    result.casts,
                    recommendedWorks,
                    similarWorks,
                    result.releaseDate,
                    result.overview,
                    result.backDropUrl,
                    result.series
                )

            }, { throwable ->
                Timber.e(throwable, "Error while loading data by work ${throwable.message}")
                //view.errorWorkDetailsLoaded()
            }).addTo(compositeDisposable)
    }

    fun reviewItemSelected(reviewViewModel: ReviewViewModel) {
        if ((reviews.indexOf(reviewViewModel) < reviews.size - 1) ||
            (reviewPage != 0 && reviewPage + 1 > totalReviewPage)
        ) {
            return
        }

        getReviewByWorkUseCase(workViewModel.type, workViewModel.id, recommendedPage + 1)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .map { it.toViewModel() }
            .observeOn(rxScheduler.mainScheduler)
            .subscribe({ result ->
                with(result) {
                    reviewPage = page
                    totalReviewPage = totalPages
                    results?.let {
                        reviews.addAll(it)
                        view.reviewLoaded(reviews)
                    }
                }
            }, { throwable ->
                Timber.e(throwable, "Error while loading reviews by work")
            }).addTo(compositeDisposable)
    }

    fun recommendationItemSelected(recommendedWorkViewModel: WorkViewModel) {
        if ((recommendedWorks.indexOf(recommendedWorkViewModel) < recommendedWorks.size - 1) ||
            (recommendedPage != 0 && recommendedPage + 1 > totalRecommendedPage)
        ) {
            return
        }

        getRecommendationByWorkUseCase(workViewModel.type, workViewModel.id, recommendedPage + 1)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .map { it.toViewModel() }
            .observeOn(rxScheduler.mainScheduler)
            .subscribe({ moviePage ->
                with(moviePage) {
                    recommendedPage = page
                    totalRecommendedPage = totalPages
                    results?.let {
                        recommendedWorks.addAll(it)
                        view.recommendationLoaded(recommendedWorks)
                    }
                }
            }, { throwable ->
                Timber.e(throwable, "Error while loading recommendations by work")
            }).addTo(compositeDisposable)
    }

    fun similarItemSelected(similarWorkViewModel: WorkViewModel) {
        if ((similarWorks.indexOf(similarWorkViewModel) < similarWorks.size - 1) ||
            (similarPage != 0 && similarPage + 1 > totalSimilarPage)
        ) {
            return
        }

        getSimilarByWorkUseCase(workViewModel.type, workViewModel.id, similarPage + 1)
            .subscribeOn(rxScheduler.ioScheduler)
            .observeOn(rxScheduler.computationScheduler)
            .map { it.toViewModel() }
            .observeOn(rxScheduler.mainScheduler)
            .subscribe({ moviePage ->
                with(moviePage) {
                    similarPage = page
                    totalSimilarPage = totalPages
                    results?.let {
                        similarWorks.addAll(it)
                        view.similarLoaded(similarWorks)
                    }
                }
            }, { throwable ->
                Timber.e(throwable, "Error while loading similar by work")
            }).addTo(compositeDisposable)
    }

    fun workClicked(itemViewHolder: Presenter.ViewHolder, workViewModel: WorkViewModel) {
        val route = workDetailsRoute.buildWorkDetailRoute(workViewModel)
        view.openWorkDetails(itemViewHolder, route)
    }

    fun castClicked(itemViewHolder: Presenter.ViewHolder, castViewModel: CastViewModel) {
        val route = castDetailsRoute.buildCastDetailRoute(castViewModel)
        view.openCastDetails(itemViewHolder, route)
    }

    fun videoClicked(videoViewModel: VideoViewModel) {
        view.openVideo(videoViewModel)
    }


    private fun GetWorkDetailsUseCase.WorkDetailsDomainWrapper.toViewModel() =
        WorkDetailsViewModelWrapper(
            isFavorite,
            videos?.map { it.toViewModel() },
            casts?.map { it.toViewModel() },
            recommended.toViewModel(),
            similar.toViewModel(),
            reviews.toViewModel(),
            releaseDate,
            overview,
            backDropUrl,
            series
        )

    data class WorkDetailsViewModelWrapper(
        val isFavorite: Boolean,
        val videos: List<VideoViewModel>?,
        val casts: List<CastViewModel>?,
        val recommended: PageViewModel<WorkViewModel>,
        val similar: PageViewModel<WorkViewModel>,
        val reviews: PageViewModel<ReviewViewModel>,
        val releaseDate: String?,
        val overview: String?,
        val backDropUrl: String?,
        val series:Pair<Boolean?, Int?>?
    )

    interface View {

        fun showProgress()

        fun hideProgress()

        fun resultSetFavoriteMovie(isFavorite: Boolean)

        fun dataLoaded(
            isFavorite: Boolean,
            reviews: List<ReviewViewModel>?,
            videos: List<VideoViewModel>?,
            casts: List<CastViewModel>?,
            recommendedWorks: List<WorkViewModel>,
            similarWorks: List<WorkViewModel>,
            releaseDate: String?,
            overview: String?,
            backDropUrl: String?,
            series: Pair<Boolean?, Int?>?
        )

        fun episodesLoaded(videos: List<VideoViewModel>?)

        fun reviewLoaded(reviews: List<ReviewViewModel>)

        fun recommendationLoaded(works: List<WorkViewModel>)

        fun similarLoaded(works: List<WorkViewModel>)

        fun errorWorkDetailsLoaded()

        fun openWorkDetails(itemViewHolder: Presenter.ViewHolder, route: Route)

        fun openCastDetails(itemViewHolder: Presenter.ViewHolder, route: Route)

        fun openVideo(videoViewModel: VideoViewModel)
    }
}
