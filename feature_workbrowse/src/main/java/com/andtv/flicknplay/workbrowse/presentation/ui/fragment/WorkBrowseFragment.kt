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

package com.andtv.flicknplay.workbrowse.presentation.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.Row
import com.andtv.flicknplay.presentation.extension.addFragment
import com.andtv.flicknplay.presentation.extension.popBackStack
import com.andtv.flicknplay.presentation.ui.fragment.ErrorFragment
import com.andtv.flicknplay.route.Route
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.presentation.presenter.WorkBrowsePresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import com.andtv.flicknplay.workbrowse.presentation.ui.diffcallback.RowDiffCallback
import com.andtv.flicknplay.workbrowse.presentation.ui.headeritem.*
import javax.inject.Inject

/**
 * Copyright (C) Flicknplay 07-02-2018.
 */
private const val ERROR_FRAGMENT_REQUEST_CODE = 1

class WorkBrowseFragment : BrowseSupportFragment(), WorkBrowsePresenter.View {

    private val rowsAdapter by lazy { ArrayObjectAdapter(ListRowPresenter()) }
    private val rowDiffCallback by lazy { RowDiffCallback() }

    @Inject
    lateinit var presenter: WorkBrowsePresenter

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivityComponent
                .workBrowseFragmentComponent()
                .create(this)
                .inject(this)
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        setWindowsParams()
    }
    @SuppressLint("ResourceAsColor", "ResourceType")
    private fun setWindowsParams(){
        brandColor = ContextCompat.getColor(requireContext(),R.color.transparent)
        //mainFragment.view?.background = Color.BLACK
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.bindTo(this.lifecycle)
        setupUIElements()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarManager.apply {
            enableProgressBar()
            setProgressBarView(
                    LayoutInflater.from(context).inflate(R.layout.view_load, null).also {
                        (view.parent as ViewGroup).addView(it)
                    })
            initialDelay = 0
        }

        adapter = rowsAdapter
        presenter.loadData()
    }

    override fun onResume() {
        super.onResume()
        presenter.addFavoriteRow(selectedPosition)
    }

    override fun onShowProgress() {
        progressBarManager.show()
    }

    override fun onHideProgress() {
        progressBarManager.hide()
    }

    override fun onDataLoaded(rows: List<Row>) {
        rowsAdapter.setItems(rows, rowDiffCallback)

        startEntranceTransition()
    }

    override fun onUpdateSelectedPosition(selectedPosition: Int) {
        this.selectedPosition = selectedPosition
    }

    override fun onErrorDataLoaded() {
        val fragment = ErrorFragment.newInstance().apply {
            setTargetFragment(this@WorkBrowseFragment, ERROR_FRAGMENT_REQUEST_CODE)
        }
        requireActivity().addFragment(fragment, ErrorFragment.TAG)
    }

    override fun openSearch(route: Route) {
        startActivity(route.intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            ERROR_FRAGMENT_REQUEST_CODE -> {
                requireActivity().popBackStack(ErrorFragment.TAG, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
                when (resultCode) {
                    Activity.RESULT_OK -> presenter.loadData()
                    else -> requireActivity().finish()
                }
            }
        }
    }

    private fun setupUIElements() {
        headersState = HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true
        setOnSearchClickedListener {
            presenter.searchClicked()
        }

        searchAffordanceColor = resources.getColor(R.color.background_color, requireActivity().theme)
        mainFragmentRegistry.registerFragment(PageRow::class.java, PageRowFragmentFactory())

        prepareEntranceTransition()
    }

    private inner class PageRowFragmentFactory : BrowseSupportFragment.FragmentFactory<Fragment>() {

        override fun createFragment(rowObj: Any): Fragment {
            presenter.refreshRows()

            val row = rowObj as Row
            when (val headerItem = row.headerItem) {
                is WorkTypeHeaderItem -> {
                    title = headerItem.name
                    return TopWorkGridFragment.newInstance(headerItem.topWorkTypeViewModel , title == getString(R.string.continue_watching))
                }
                is GenreHeaderItem -> {
                    title = headerItem.name
                    return GenreWorkGridFragment.newInstance(headerItem.genreViewModel)
                }
                is AboutHeaderItem -> {
                    title = ""
                    return AboutFragment.newInstance()
                }

                is AccountHeaderItem -> {
                    title = ""
                    return AccountSettingFragment.newInstance()
                }

                is SettingHeaderItem -> {
                    title = ""
                    return SettingFragment.newInstance()
                }

            }

            throw IllegalArgumentException(String.format("Invalid row %s", rowObj))
        }
    }


    companion object {

        fun newInstance() = WorkBrowseFragment()
    }
}
