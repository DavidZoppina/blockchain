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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.andtv.flicknplay.model.presentation.model.WorkViewModel
import com.andtv.flicknplay.model.presentation.model.loadBackdrop
import com.andtv.flicknplay.presentation.extension.addFragment
import com.andtv.flicknplay.presentation.extension.popBackStack
import com.andtv.flicknplay.presentation.ui.fragment.ErrorFragment
import com.andtv.flicknplay.presentation.ui.setting.SettingShared
import com.andtv.flicknplay.route.Route
import com.andtv.flicknplay.workbrowse.presentation.model.GenreViewModel
import com.andtv.flicknplay.workbrowse.presentation.presenter.GenreGridPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import javax.inject.Inject

/**
 * Copyright (C) Flicknplay 11-02-2018.
 */
private const val GENRE = "GENRE"
private const val ERROR_FRAGMENT_REQUEST_CODE = 1

class GenreWorkGridFragment : BaseWorkGridFragment(), GenreGridPresenter.View {

    private val genreViewModel by lazy { arguments?.getSerializable(GENRE) as GenreViewModel }

    @Inject
    lateinit var presenter: GenreGridPresenter

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivityComponent
                .genreWorkGridFragmentComponent()
                .create(this)
                .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.bindTo(lifecycle)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadWorkByGenre(genreViewModel)
    }

    override fun lastRowLoaded() {
        presenter.loadWorkByGenre(genreViewModel)
    }

    override fun workSelected(workSelected: WorkViewModel) {
        presenter.countTimerLoadBackdropImage(workSelected)
    }

    override fun workClicked(itemViewHolder: Presenter.ViewHolder, workViewModel: WorkViewModel) {
        presenter.workClicked(itemViewHolder, workViewModel)
    }

    override fun onShowProgress() {
        progressBarManager.show()
    }

    override fun onHideProgress() {
        progressBarManager.hide()
    }

    override fun onWorksLoaded(works: List<WorkViewModel>) {
        rowsAdapter.setItems(works, workDiffCallback)
        mainFragmentAdapter.fragmentHost.notifyDataReady(mainFragmentAdapter)
    }

    override fun loadBackdropImage(workViewModel: WorkViewModel) {
        workViewModel.loadBackdrop(requireContext()) {
            backgroundManager?.setBitmap(it)
        }
    }

    override fun onErrorWorksLoaded() {
        val fragment = ErrorFragment.newInstance().apply {
            setTargetFragment(this@GenreWorkGridFragment, ERROR_FRAGMENT_REQUEST_CODE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            ERROR_FRAGMENT_REQUEST_CODE -> {
                requireActivity().popBackStack(ErrorFragment.TAG, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
                when (resultCode) {
                    Activity.RESULT_OK -> presenter.loadWorkByGenre(genreViewModel)
                    else -> requireActivity().finish()
                }
            }
        }
    }

    companion object {

        fun newInstance(genreViewModel: GenreViewModel) =
                GenreWorkGridFragment().apply {
                    arguments = bundleOf(
                            GENRE to genreViewModel
                    )
                }
    }
}
