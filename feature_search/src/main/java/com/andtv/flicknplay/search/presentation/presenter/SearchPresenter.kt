/*
 *
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

package com.andtv.flicknplay.search.presentation.presenter

import android.widget.Toast
import androidx.leanback.widget.Presenter
import com.andtv.flicknplay.model.presentation.mapper.toViewModel
import com.andtv.flicknplay.model.presentation.model.WorkViewModel
import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.presentation.extension.addTo
import com.andtv.flicknplay.presentation.extension.disposeIfRunning
import com.andtv.flicknplay.presentation.extension.hasNoContent
import com.andtv.flicknplay.presentation.presenter.AutoDisposablePresenter
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.route.Route
import com.andtv.flicknplay.route.workdetail.WorkDetailsRoute
import com.andtv.flicknplay.search.domain.SearchByQueryUseCase
import com.andtv.flicknplay.search.domain.SearchWorksByQueryUseCase
import com.andtv.flicknplay.workdetail.presentation.presenter.WorkDetailsPresenter
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


private const val BACKGROUND_UPDATE_DELAY = 300L

/**
 *
 */
@FragmentScope
class SearchPresenter @Inject constructor(
    private val view: View,
    private val searchWorksByQueryUseCase: SearchWorksByQueryUseCase,
    private val searchByQueryUseCase: SearchByQueryUseCase,
    private val workDetailsRoute: WorkDetailsRoute,
    private val rxScheduler: RxScheduler
) : AutoDisposablePresenter() {

    private var query: String = ""
    private var resultMoviePage = 0
    private var totalMoviePage = 0
    private var resultTvShowPage = 0
    private var totalTvShowPage = 0
    private var searchWorkDisposable: Disposable? = null
    private var loadBackdropImageDisposable: Disposable? = null

    private val movies by lazy { mutableListOf<WorkViewModel>() }
    private val tvShows by lazy { mutableListOf<WorkViewModel>() }

    override fun dispose() {
        searchWorkDisposable?.disposeIfRunning()
        loadBackdropImageDisposable?.disposeIfRunning()
        super.dispose()
    }

    fun searchWorksByQuery(text: String?) {
        searchWorkDisposable?.disposeIfRunning()

        if (text == null || text.hasNoContent()) {
            view.onHideProgress()
            view.onClear()
            return
        }

        query = text
        resultMoviePage = 0
        totalMoviePage = 0
        resultTvShowPage = 0
        totalTvShowPage = 0
        movies.clear()
        tvShows.clear()

        searchWorkDisposable = searchWorksByQueryUseCase(query)
                .subscribeOn(rxScheduler.ioScheduler)
                .observeOn(rxScheduler.computationScheduler)
                .map { result ->
                    val viewModel = result.toViewModel()
                    val moviePage = viewModel.copy().also { it -> it.results = viewModel.results?.filter { it.isSeries == false } }
                    val tvShowPage = viewModel.copy().also { it -> it.results = viewModel.results?.filter { it.isSeries == true } }
                    moviePage to tvShowPage
                }
                .observeOn(rxScheduler.mainScheduler)
                .doOnSubscribe { view.onShowProgress() }
                .doFinally { view.onHideProgress() }
                .subscribe({ pair ->
                    with(pair.first) {
                        resultMoviePage = page
                        totalMoviePage = totalPages
                        results?.let {
                            movies.addAll(it)
                        }
                    }

                    with(pair.second) {
                        resultTvShowPage = page
                        totalTvShowPage = totalPages
                        results?.let {
                            tvShows.addAll(it)
                        }
                    }

                    if (movies.isNotEmpty() || tvShows.isNotEmpty()) {
                        view.onResultLoaded(movies, tvShows)
                    } else {
                        view.onClear()
                    }
                }, { throwable ->
                    //Timber.e(throwable, "Error while searching by query $query")
                    //view.onErrorSearch()
                    view.onClear()
                })
    }

    fun loadMovies() {
        if (resultMoviePage != 0 && resultMoviePage + 1 > totalMoviePage) {
            return
        }

        searchByQueryUseCase(query, resultMoviePage + 1)
                .subscribeOn(rxScheduler.ioScheduler)
                .observeOn(rxScheduler.computationScheduler)
                .map { it.toViewModel().also { it.results = it.results?.filter { it.isSeries == false } } }
                .observeOn(rxScheduler.mainScheduler)
                .subscribe({ moviePage ->
                    with(moviePage) {
                        resultMoviePage = page
                        totalMoviePage = totalPages
                        results?.let { works ->
                            movies.addAll(works)
                            view.onMoviesLoaded(movies)
                        }
                    }
                }, { throwable ->
                    Timber.e(throwable, "Error while loading movies by query")
                }).addTo(compositeDisposable)
    }

    fun loadTvShows() {
        if (resultTvShowPage != 0 && resultTvShowPage + 1 > totalTvShowPage) {
            return
        }

        searchByQueryUseCase(query, resultTvShowPage + 1)
                .subscribeOn(rxScheduler.ioScheduler)
                .observeOn(rxScheduler.computationScheduler)
                .map { it.toViewModel().also { it.results = it.results?.filter { it.isSeries == true } } }
                .observeOn(rxScheduler.mainScheduler)
                .subscribe({ tvShowPage ->
                    with(tvShowPage) {
                        resultTvShowPage = page
                        totalTvShowPage = totalPages
                        results?.let { works ->
                            tvShows.addAll(works)
                            view.onTvShowsLoaded(tvShows)
                        }
                    }
                }, { throwable ->
                    Timber.e(throwable, "Error while loading tv shows by query")
                }).addTo(compositeDisposable)
    }

    fun countTimerLoadBackdropImage(workViewModel: WorkViewModel) {
        loadBackdropImageDisposable?.disposeIfRunning()
        loadBackdropImageDisposable = Completable
                .timer(BACKGROUND_UPDATE_DELAY, TimeUnit.MILLISECONDS)
                .subscribeOn(rxScheduler.ioScheduler)
                .observeOn(rxScheduler.mainScheduler)
                .subscribe({
                    view.loadBackdropImage(workViewModel)
                }, { throwable ->
                    Timber.e(throwable, "Error while loading backdrop image")
                })
    }

    fun workClicked(itemViewHolder: Presenter.ViewHolder, workViewModel: WorkViewModel) {
        val route = workDetailsRoute.buildWorkDetailRoute(workViewModel)
        view.openWorkDetails(itemViewHolder, route)

    }

    interface View {

        fun onShowProgress()

        fun onHideProgress()

        fun onClear()

        fun onResultLoaded(movies: List<WorkViewModel>, tvShows: List<WorkViewModel>)

        fun onMoviesLoaded(movies: List<WorkViewModel>)

        fun onTvShowsLoaded(tvShows: List<WorkViewModel>)

        fun loadBackdropImage(workViewModel: WorkViewModel)

        fun onErrorSearch()

        fun openWorkDetails(itemViewHolder: Presenter.ViewHolder, route: Route)
    }
}
