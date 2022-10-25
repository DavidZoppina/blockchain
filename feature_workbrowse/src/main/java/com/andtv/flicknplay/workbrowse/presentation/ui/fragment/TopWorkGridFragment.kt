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
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.leanback.R
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.andtv.flicknplay.model.presentation.model.WorkViewModel
import com.andtv.flicknplay.model.presentation.model.loadBackdrop
import com.andtv.flicknplay.presentation.extension.addFragment
import com.andtv.flicknplay.presentation.extension.popBackStack
import com.andtv.flicknplay.presentation.ui.fragment.ErrorFragment
import com.andtv.flicknplay.presentation.ui.setting.SettingShared
import com.andtv.flicknplay.route.Route
import com.andtv.flicknplay.workbrowse.presentation.model.TopWorkTypeViewModel
import com.andtv.flicknplay.workbrowse.presentation.presenter.TopWorkGridPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import com.andtv.flicknplay.workdetail.presentation.ui.activity.PlayerActivity
import javax.inject.Inject

/**
 * Copyright (C) Flicknplay 11-02-2018.
 */
private const val TYPE = "TYPE"
private const val CONTINUE_WATCHING = "CONTINUE_WATCHING"
private const val ERROR_FRAGMENT_REQUEST_CODE = 1
private const val WORK_DETAILS_REQUEST_CODE = 2

class TopWorkGridFragment : BaseWorkGridFragment(), TopWorkGridPresenter.View {

    private val topWorkTypeViewModel by lazy { arguments?.getSerializable(TYPE) as TopWorkTypeViewModel }
    private val isContinueWatchingItem by lazy { arguments?.getBoolean(CONTINUE_WATCHING)}

    @Inject
    lateinit var presenter: TopWorkGridPresenter

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivityComponent
                .topWorkGridFragmentComponent()
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
        presenter.loadWorkPageByType(topWorkTypeViewModel)
    }

    override fun lastRowLoaded() {
        presenter.loadWorkPageByType(topWorkTypeViewModel)
    }

    override fun workSelected(workSelected: WorkViewModel) {
        presenter.countTimerLoadBackdropImage(workSelected)
    }

    override fun workClicked(itemViewHolder: Presenter.ViewHolder, workViewModel: WorkViewModel) {
        isContinueWatchingItem?.let { presenter.isContinueWatchingItem  = it }
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
            setTargetFragment(this@TopWorkGridFragment, ERROR_FRAGMENT_REQUEST_CODE)
        }
        requireActivity().addFragment(fragment, ErrorFragment.TAG)
    }

    override fun openWorkDetails(itemViewHolder: Presenter.ViewHolder, route: Route) {
        val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                (itemViewHolder.view as ImageCardView).mainImageView,
                SettingShared.SHARED_ELEMENT_NAME
        ).toBundle()
        startActivityForResult(
                route.intent,
                WORK_DETAILS_REQUEST_CODE,
                bundle
        )

    }

    override fun openPlayerViewToContinueWatching(workViewModel: WorkViewModel) {

        val intent = if (workViewModel.videos?.get(0)?.type == "video" || workViewModel.videos?.get(0)?.type == "stream") {
            Intent(activity, PlayerActivity::class.java).apply {
                putExtra(PlayerActivity.urlKey, workViewModel.videos?.get(0)?.url)
                putExtra(PlayerActivity.movieTitle, workViewModel.title)
                putExtra(PlayerActivity.movieId, workViewModel.videos?.get(0)?.id.toString())
                putExtra(PlayerActivity.seekTimeKey,workViewModel.seekTime)

            }
        } else {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(workViewModel.videos?.get(0)?.url)
            )
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), com.andtv.flicknplay.workdetail.R.string.failed_open_video, Toast.LENGTH_LONG).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            ERROR_FRAGMENT_REQUEST_CODE -> {
                requireActivity().popBackStack(ErrorFragment.TAG, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
                when (resultCode) {
                    Activity.RESULT_OK -> presenter.loadWorkPageByType(topWorkTypeViewModel)
                    else -> requireActivity().finish()
                }
            }
            WORK_DETAILS_REQUEST_CODE -> presenter.refreshPage(topWorkTypeViewModel)
        }
    }

    companion object {

        fun newInstance(topWorkTypeViewModel: TopWorkTypeViewModel, isContinueWatching: Boolean) =
                TopWorkGridFragment().apply {
                    arguments = bundleOf(
                            TYPE to topWorkTypeViewModel,
                        CONTINUE_WATCHING to isContinueWatching
                    )
                }
    }
}
