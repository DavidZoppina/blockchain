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

import androidx.leanback.widget.DividerRow
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.Row
import androidx.leanback.widget.SectionRow
import com.andtv.flicknplay.model.domain.PageDomainModel
import com.andtv.flicknplay.model.domain.WorkDomainModel
import com.andtv.flicknplay.presentation.di.annotation.FragmentScope
import com.andtv.flicknplay.presentation.extension.addTo
import com.andtv.flicknplay.presentation.platform.Resource
import com.andtv.flicknplay.presentation.presenter.AutoDisposablePresenter
import com.andtv.flicknplay.presentation.scheduler.RxScheduler
import com.andtv.flicknplay.route.Route
import com.andtv.flicknplay.route.search.SearchRoute
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.domain.GetWorkBrowseDetailsUseCase
import com.andtv.flicknplay.workbrowse.domain.HasFavoriteUseCase
import com.andtv.flicknplay.workbrowse.domain.model.toViewModel
import com.andtv.flicknplay.workbrowse.presentation.model.GenreViewModel
import com.andtv.flicknplay.workbrowse.presentation.model.TopWorkTypeViewModel
import com.andtv.flicknplay.workbrowse.presentation.ui.headeritem.*
import javax.inject.Inject
import timber.log.Timber

/**
 * Copyright (C) Flicknplay 06-02-2018.
 */
private const val TOP_WORK_LIST_ID = 1L
private const val WORK_GENRE_ID = 2L
private const val ABOUT_ID = 3L
private const val LOGIN_ID = 4L
private const val SETTING_ID = 5L
private const val CONTINUE_WATCHING_LIST_ID = 6L
private const val ACCOUNT_ID = 7L
private const val FAVORITE_INDEX = 0
private const val INVALID_INDEX = -1

@FragmentScope
class WorkBrowsePresenter @Inject constructor(
    private val view: View,
    private val hasFavoriteUseCase: HasFavoriteUseCase,
    private val getWorkBrowseDetailsUseCase: GetWorkBrowseDetailsUseCase,
    private val searchRoute: SearchRoute,
    private val resource: Resource,
    private val rxScheduler: RxScheduler
) : AutoDisposablePresenter() {

    private var refreshRows = false
    private val rows by lazy { mutableListOf<Row>() }
    private val favoritePageRow by lazy {
        PageRow(
                WorkTypeHeaderItem(
                        TOP_WORK_LIST_ID,
                        resource.getStringResource(TopWorkTypeViewModel.FAVORITES_MOVIES.resource),
                        TopWorkTypeViewModel.FAVORITES_MOVIES
                )
        )
    }

    fun loadData() {
        getWorkBrowseDetailsUseCase()
                .subscribeOn(rxScheduler.ioScheduler)
                .observeOn(rxScheduler.computationScheduler)
                .map { result ->
                        result.toViewModel()
//                    Triple(viewModel.hasFavoriteMovie, viewModel.movieGenreList, viewModel.tvShowGenreList)
                }
                .observeOn(rxScheduler.mainScheduler)
                .doOnSubscribe { view.onShowProgress() }
                .doFinally { view.onHideProgress() }
                .subscribe({ result ->
//                    val hasFavoriteMovie = result.first
//                    val movieGenres = result.second
//                    val tvShowGenres = result.third

                    buildRowList(result.hasFavoriteMovie, result.movieGenreList, result.tvShowGenreList, result.continueWatchingMovies)

                    view.onDataLoaded(rows)
                }, { throwable ->
                    Timber.e(throwable, "Error while loading data")
                    view.onErrorDataLoaded()
                }).addTo(compositeDisposable)
    }

    fun addFavoriteRow(selectedPosition: Int) {
        if (rows.isEmpty()) {
            return
        }

        hasFavoriteUseCase()
                .subscribeOn(rxScheduler.ioScheduler)
                .observeOn(rxScheduler.mainScheduler)
                .subscribe({ hasFavorite ->
                    if (hasFavorite) {
                        if (rows.indexOf(favoritePageRow) == INVALID_INDEX) {
                            rows.add(FAVORITE_INDEX, favoritePageRow)
                            view.onDataLoaded(rows)
                        }
                    } else {
                        if (rows.indexOf(favoritePageRow) == FAVORITE_INDEX) {
                            rows.remove(favoritePageRow)
                            if (selectedPosition == FAVORITE_INDEX) {
                                refreshRows = true
                                view.onUpdateSelectedPosition(FAVORITE_INDEX + 3)
                            }
                        }
                    }
                }, { throwable ->
                    Timber.e(throwable, "Error while checking if has any work as favorite")
                }).addTo(compositeDisposable)
    }

    fun refreshRows() {
        if (refreshRows) {
            refreshRows = false
            view.onDataLoaded(rows)
        }
    }

    fun searchClicked() {
        val route = searchRoute.buildSearchRoute()
        view.openSearch(route)
    }

    private fun buildRowList(
        hasFavorite: Boolean,
        movieGenres: List<GenreViewModel>?,
        tvShowGenres: List<GenreViewModel>?,
        continueWatchingMovies: PageDomainModel<WorkDomainModel>?
    ) {
        rows.run {
            if (hasFavorite) {
                add(favoritePageRow)
            }


            continueWatchingMovies?.results?.let {
                if(it.size>0)
                {
                    add(PageRow(WorkTypeHeaderItem(CONTINUE_WATCHING_LIST_ID, resource.getStringResource(TopWorkTypeViewModel.CONTINUE_WATCHING.resource), TopWorkTypeViewModel.CONTINUE_WATCHING)))
                }
            }
            add(DividerRow())
            add(SectionRow(resource.getStringResource(R.string.movies)))
           // add(PageRow(WorkTypeHeaderItem(TOP_WORK_LIST_ID, resource.getStringResource(TopWorkTypeViewModel.NOW_PLAYING_MOVIES.resource), TopWorkTypeViewModel.NOW_PLAYING_MOVIES)))
            add(PageRow(WorkTypeHeaderItem(TOP_WORK_LIST_ID, resource.getStringResource(TopWorkTypeViewModel.POPULAR_MOVIES.resource), TopWorkTypeViewModel.POPULAR_MOVIES)))
            add(PageRow(WorkTypeHeaderItem(TOP_WORK_LIST_ID, resource.getStringResource(TopWorkTypeViewModel.TOP_RATED_MOVIES.resource), TopWorkTypeViewModel.TOP_RATED_MOVIES)))
            add(PageRow(WorkTypeHeaderItem(TOP_WORK_LIST_ID, resource.getStringResource(TopWorkTypeViewModel.UP_COMING_MOVIES.resource), TopWorkTypeViewModel.UP_COMING_MOVIES)))

            movieGenres?.forEach {
                add(PageRow(GenreHeaderItem(WORK_GENRE_ID, it)))
            }
            add(DividerRow())

            add(SectionRow(resource.getStringResource(R.string.tv_shows)))
            add(PageRow(WorkTypeHeaderItem(TOP_WORK_LIST_ID, resource.getStringResource(TopWorkTypeViewModel.AIRING_TODAY_TV_SHOWS.resource), TopWorkTypeViewModel.AIRING_TODAY_TV_SHOWS)))
         //   add(PageRow(WorkTypeHeaderItem(TOP_WORK_LIST_ID, resource.getStringResource(TopWorkTypeViewModel.ON_THE_AIR_TV_SHOWS.resource), TopWorkTypeViewModel.ON_THE_AIR_TV_SHOWS)))
            add(PageRow(WorkTypeHeaderItem(TOP_WORK_LIST_ID, resource.getStringResource(TopWorkTypeViewModel.TOP_RATED_TV_SHOWS.resource), TopWorkTypeViewModel.TOP_RATED_TV_SHOWS)))
            add(PageRow(WorkTypeHeaderItem(TOP_WORK_LIST_ID, resource.getStringResource(TopWorkTypeViewModel.POPULAR_TV_SHOWS.resource), TopWorkTypeViewModel.POPULAR_TV_SHOWS)))

            tvShowGenres?.forEach {
                add(PageRow(GenreHeaderItem(WORK_GENRE_ID, it)))
            }
            add(DividerRow())
            add(PageRow(SettingHeaderItem(SETTING_ID, resource.getStringResource(R.string.setting))))
            add(PageRow(AccountHeaderItem(ACCOUNT_ID, resource.getStringResource(R.string.account))))
            add(PageRow(AboutHeaderItem(ABOUT_ID, resource.getStringResource(R.string.about))))
        }
    }

    interface View {

        fun onShowProgress()

        fun onHideProgress()

        fun onDataLoaded(rows: List<Row>)

        fun onUpdateSelectedPosition(selectedPosition: Int)

        fun onErrorDataLoaded()

        fun openSearch(route: Route)
    }
}
