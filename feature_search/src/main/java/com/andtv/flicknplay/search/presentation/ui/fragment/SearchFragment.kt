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

package com.andtv.flicknplay.search.presentation.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.ProgressBarManager
import androidx.leanback.app.SearchSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.Presenter
import com.andtv.flicknplay.model.presentation.model.WorkViewModel
import com.andtv.flicknplay.model.presentation.model.loadBackdrop
import com.andtv.flicknplay.presentation.extension.addFragment
import com.andtv.flicknplay.presentation.extension.popBackStack
import com.andtv.flicknplay.presentation.ui.diffcallback.WorkDiffCallback
import com.andtv.flicknplay.presentation.ui.fragment.ErrorFragment
import com.andtv.flicknplay.presentation.ui.render.WorkCardRenderer
import com.andtv.flicknplay.presentation.ui.setting.SettingShared
import com.andtv.flicknplay.route.Route
import com.andtv.flicknplay.search.R
import com.andtv.flicknplay.search.presentation.presenter.SearchPresenter
import com.andtv.flicknplay.search.presentation.ui.activity.SearchActivity
import java.lang.Exception
import javax.inject.Inject

private const val SEARCH_FRAGMENT_REQUEST_CODE = 1
private const val ERROR_FRAGMENT_REQUEST_CODE = 2
private const val MOVIE_HEADER_ID = 1
private const val TV_SHOW_HEADER_ID = 2

/**
 *
 */
class SearchFragment : SearchSupportFragment(), SearchPresenter.View, SearchSupportFragment.SearchResultProvider {

    private val rowsAdapter by lazy { ArrayObjectAdapter(ListRowPresenter()) }
    private val movieRowAdapter by lazy { ArrayObjectAdapter(WorkCardRenderer()) }
    private val tvShowRowAdapter by lazy { ArrayObjectAdapter(WorkCardRenderer()) }
    private val backgroundManager by lazy { BackgroundManager.getInstance(activity) }
    private val progressBarManager by lazy { ProgressBarManager() }
    private val workDiffCallback by lazy { WorkDiffCallback() }

    @Inject
    lateinit var presenter: SearchPresenter

    private var query: String? = null
    private var workSelected: WorkViewModel? = null

    override fun onAttach(context: Context) {
        (requireActivity() as SearchActivity).searchActivityComponent
            .searchFragmentComponent()
            .create(this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.bindTo(this.lifecycle)

        setupUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(resources.getColor(androidx.leanback.R.color.lb_playback_controls_background_light, null))
        progressBarManager.apply {
            enableProgressBar()
            setProgressBarView(
                LayoutInflater.from(context).inflate(R.layout.view_load, null).also {
                    (view.parent as ViewGroup).addView(it)
                }
            )
            initialDelay = 0
        }
    }

    override fun onResume() {
        super.onResume()
        loadBackdropImage()
    }

    override fun onShowProgress() {
        progressBarManager.show()
    }

    override fun onHideProgress() {
        progressBarManager.hide()
    }

    override fun onClear() {
        backgroundManager.setBitmap(null)
        rowsAdapter.clear()
        rowsAdapter.add(
            ListRow(
                HeaderItem(0, getString(R.string.no_results)),
                ArrayObjectAdapter(WorkCardRenderer())
            )
        )
    }

    override fun onResultLoaded(movies: List<WorkViewModel>, tvShows: List<WorkViewModel>) {
        rowsAdapter.clear()

        if (movies.isNotEmpty()) {
            val header = HeaderItem(MOVIE_HEADER_ID.toLong(), getString(R.string.movies))
            movieRowAdapter.setItems(movies, workDiffCallback)
            rowsAdapter.add(ListRow(header, movieRowAdapter))
        }
        if (tvShows.isNotEmpty()) {
            val header = HeaderItem(TV_SHOW_HEADER_ID.toLong(), getString(R.string.tv_shows))
            tvShowRowAdapter.setItems(tvShows, workDiffCallback)
            rowsAdapter.add(ListRow(header, tvShowRowAdapter))
        }
    }

    override fun onMoviesLoaded(movies: List<WorkViewModel>) {
        movieRowAdapter.setItems(movies, workDiffCallback)
    }

    override fun onTvShowsLoaded(tvShows: List<WorkViewModel>) {
        tvShowRowAdapter.setItems(tvShows, workDiffCallback)
    }

    override fun loadBackdropImage(workViewModel: WorkViewModel) {
        workViewModel.loadBackdrop(requireContext()) {
            backgroundManager.setBitmap(it)
        }
    }

    override fun onErrorSearch() {
        val fragment = ErrorFragment.newInstance().apply {
            setTargetFragment(this@SearchFragment, ERROR_FRAGMENT_REQUEST_CODE)
        }
        requireActivity().addFragment(fragment, ErrorFragment.TAG)
    }

    override fun openWorkDetails(itemViewHolder: Presenter.ViewHolder, route: Route) {
        val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                (itemViewHolder.view as ImageCardView).mainImageView,
                SettingShared.SHARED_ELEMENT_NAME
        ).toBundle()
        startActivity(route.intent, bundle)
    }

    override fun getResultsAdapter() = rowsAdapter

    override fun onQueryTextChange(text: String): Boolean {

        query = text
        presenter.searchWorksByQuery(query)
        return true
    }

    override fun onQueryTextSubmit(text: String): Boolean {
        query = text
        presenter.searchWorksByQuery(query)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SEARCH_FRAGMENT_REQUEST_CODE -> {
                view?.requestFocus()
            }
            ERROR_FRAGMENT_REQUEST_CODE -> {
                requireActivity().popBackStack(ErrorFragment.TAG, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
                when (resultCode) {
                    Activity.RESULT_OK -> presenter.searchWorksByQuery(query)
                    else -> requireActivity().finish()
                }
            }
        }
    }

    private fun setupUI() {
        setSearchResultProvider(this)
        setOnItemViewSelectedListener { _, item, _, row ->
            workSelected = item as WorkViewModel?
            loadBackdropImage()

            when (row?.run { id.toInt() }) {
                MOVIE_HEADER_ID -> if (movieRowAdapter.indexOf(workSelected) >= movieRowAdapter.size() - 1) {
                    presenter.loadMovies()
                }
                TV_SHOW_HEADER_ID -> if (tvShowRowAdapter.indexOf(workSelected) >= tvShowRowAdapter.size() - 1) {
                    presenter.loadTvShows()
                }
            }
        }
        setOnItemViewClickedListener { itemViewHolder, item, _, _ ->
            if (item is WorkViewModel) {
                presenter.workClicked(itemViewHolder, item)
            }
        }
    }

    private fun loadBackdropImage() {
        try {
            workSelected?.let {
                presenter.countTimerLoadBackdropImage(it)
            }
        }catch (exe:Exception){
            exe.printStackTrace()
        }

    }

    companion object {

        fun newInstance(): SearchFragment = SearchFragment()
    }
}
