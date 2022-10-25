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

package com.andtv.flicknplay.workdetail.presentation.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import com.andtv.flicknplay.model.presentation.model.CastViewModel
import com.andtv.flicknplay.model.presentation.model.WorkViewModel
import com.andtv.flicknplay.model.presentation.model.loadBackdrop
import com.andtv.flicknplay.model.presentation.model.loadPoster
import com.andtv.flicknplay.presentation.extension.addFragment
import com.andtv.flicknplay.presentation.extension.isNotNullOrEmpty
import com.andtv.flicknplay.presentation.extension.popBackStack
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.presentation.ui.diffcallback.WorkDiffCallback
import com.andtv.flicknplay.presentation.ui.fragment.ErrorFragment
import com.andtv.flicknplay.presentation.ui.render.WorkCardRenderer
import com.andtv.flicknplay.presentation.ui.setting.SettingShared
import com.andtv.flicknplay.route.Route
import com.andtv.flicknplay.workdetail.R
import com.andtv.flicknplay.workdetail.presentation.model.ReviewViewModel
import com.andtv.flicknplay.workdetail.presentation.model.SeasonViewModel
import com.andtv.flicknplay.workdetail.presentation.model.VideoViewModel
import com.andtv.flicknplay.workdetail.presentation.presenter.WorkDetailsPresenter
import com.andtv.flicknplay.workdetail.presentation.ui.activity.PlayerActivity
import com.andtv.flicknplay.workdetail.presentation.ui.activity.WorkDetailsActivity
import com.andtv.flicknplay.workdetail.presentation.ui.diffcallback.ReviewDiffCallback
import com.andtv.flicknplay.workdetail.presentation.ui.diffcallback.VideoDiffCallback
import com.andtv.flicknplay.workdetail.presentation.ui.render.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private const val WORK = "WORK"
private const val ERROR_FRAGMENT_REQUEST_CODE = 1
private const val ACTION_FAVORITE = 1
private const val ACTION_REVIEWS = 2
private const val ACTION_VIDEOS = 3
private const val ACTION_CAST = 4
private const val ACTION_RECOMMENDED = 5
private const val ACTION_SIMILAR = 6
private const val ACTION_EPISODES = 7
private const val ACTION_EPISODES_LIST = 7
private const val REVIEW_HEADER_ID = 1
private const val VIDEO_HEADER_ID = 2
private const val RECOMMENDED_HEADER_ID = 4
private const val SIMILAR_HEADER_ID = 5
private const val CAST_HEAD_ID = 6
private const val EPISODES_HEAD_ID = 7
private const val EPISODES_LIST_ID = 8

/**
 * Copyright (C) Flicknplay 07-02-2018.
 */
class WorkDetailsFragment : DetailsSupportFragment(),
    WorkDetailsPresenter.View,
    OnItemViewSelectedListener,
    OnItemViewClickedListener {

    private var lastViewSeasonNumber = 1

    private val actionAdapter by lazy { ArrayObjectAdapter() }
    private val seasonActionAdapter by lazy { ArrayObjectAdapter(SeasonCardRender()) }
    private val reviewRowAdapter by lazy { ArrayObjectAdapter(ReviewCardRender()) }
    private val moviesVideoRowAdapter by lazy { ArrayObjectAdapter(VideoCardRender()) }
    private val trailersVideoRowAdapter by lazy { ArrayObjectAdapter(VideoCardRender()) }
    private val episodeVideoRowAdapter by lazy { ArrayObjectAdapter(VideoCardRender()) }
    private val castRowAdapter by lazy { ArrayObjectAdapter(CastCardRender()) }
    private val recommendedRowAdapter by lazy { ArrayObjectAdapter(WorkCardRenderer()) }
    private val similarRowAdapter by lazy { ArrayObjectAdapter(WorkCardRenderer()) }
    private val mainAdapter by lazy { ArrayObjectAdapter(presenterSelector) }
    private val workDiffCallback by lazy { WorkDiffCallback() }
    private val reviewDiffCallback by lazy { ReviewDiffCallback() }
    private val episodeDiffCallback by lazy { VideoDiffCallback() }
    private val presenterSelector by lazy {
        ClassPresenterSelector().apply {
            addClassPresenter(ListRow::class.java, ListRowPresenter())
        }
    }

    private var player:CustomMediaPlayerGlue? = null
    private val videoPlayBackDelay = 5000L

    private  var coverImage :Bitmap? =null
    private val detailsBackground by lazy {
        MyDetailsSupportFragmentBackgroundController(this).apply {

            parallaxDrawableMaxOffset = 200
            enableParallax()

        }
    }
    private val workViewModel by lazy { arguments?.getSerializable(WORK) as WorkViewModel }

    @Inject
    lateinit var presenter: WorkDetailsPresenter

    private lateinit var favoriteAction: Action
    private lateinit var detailsOverviewRow: DetailsOverviewRow
    private lateinit var seasonListRow: DetailsOverviewRow




    override fun onAttach(context: Context) {
        (requireActivity() as WorkDetailsActivity).workDetailsActivityComponent
            .workDetailsFragmentComponent()
            .create(this, workViewModel)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.bindTo(this.lifecycle)

        setupDetailsOverviewRow()
        setupDetailsOverviewRowPresenter()
        adapter = mainAdapter




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
        
        presenter.loadData()
    }

    override fun showProgress() {
        progressBarManager.show()
    }

    override fun hideProgress() {
        progressBarManager.hide()
    }

    override fun resultSetFavoriteMovie(isFavorite: Boolean) {
        favoriteAction.label1 = resources.getString(R.string.remove_favorites).takeIf { isFavorite }
            ?: run { resources.getString(R.string.save_favorites) }
        actionAdapter.notifyItemRangeChanged(actionAdapter.indexOf(favoriteAction), 1)
    }

    @SuppressLint("RestrictedApi")
    override fun dataLoaded(
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
    ) {
        favoriteAction = Action(
            ACTION_FAVORITE.toLong(),
            resources.getString(R.string.remove_favorites).takeIf { isFavorite }
                ?: resources.getString(R.string.save_favorites)
        )
        actionAdapter.add(favoriteAction)
        val isSeries = series?.first
        val seasonCount = series?.second

        val movies =
            videos?.filter { it.type == "video" || it.type == "stream" }?.sortedBy { it.order }
        val trailers = videos?.filter { it.type == "embed" }?.sortedBy { it.order }

        if (movies != null && movies.isNotNullOrEmpty())
        playBackVideo(movies.get(0).url,backDropUrl)



        if (movies.isNotNullOrEmpty()) {
            actionAdapter.add(Action(ACTION_VIDEOS.toLong(), getString(R.string.play)))
            moviesVideoRowAdapter.addAll(0, movies)
            mainAdapter.add(
                ListRow(
                    HeaderItem(VIDEO_HEADER_ID.toLong(), getString(R.string.play)),
                    moviesVideoRowAdapter
                )
            )
        }

        if (reviews.isNotNullOrEmpty()) {
            actionAdapter.add(Action(ACTION_REVIEWS.toLong(), getString(R.string.reviews)))
            reviewRowAdapter.setItems(reviews, reviewDiffCallback)
            mainAdapter.add(
                ListRow(
                    HeaderItem(
                        REVIEW_HEADER_ID.toLong(),
                        getString(R.string.reviews)
                    ), reviewRowAdapter
                )
            )
        }




        if (trailers.isNotNullOrEmpty()) {
            actionAdapter.add(Action(ACTION_VIDEOS.toLong(), getString(R.string.videos)))
            trailersVideoRowAdapter.addAll(0, trailers)
            mainAdapter.add(
                ListRow(
                    HeaderItem(
                        VIDEO_HEADER_ID.toLong(),
                        getString(R.string.videos)
                    ), trailersVideoRowAdapter
                )
            )
        }


        if(isSeries != null && isSeries ) {

            workViewModel.isSeries = isSeries

            var seasons:MutableList<SeasonViewModel> = mutableListOf()
            for (i in 0 until seasonCount!!)
            {
                seasons.add(SeasonViewModel(getString(R.string.season)+" ${i+1}", i+1))
            }

            actionAdapter.add(Action(ACTION_EPISODES.toLong(), getString(R.string.episodes)))
            seasonActionAdapter.addAll(0, seasons)
            mainAdapter.add(
                ListRow(
                    HeaderItem(
                        EPISODES_HEAD_ID.toLong(),
                        getString(R.string.episodes)
                    ), seasonActionAdapter
                )
            )

            episodeVideoRowAdapter.setItems(listOf<WorkViewModel>(), episodeDiffCallback)
            mainAdapter.add(
                ListRow(
                    HeaderItem(
                        EPISODES_LIST_ID.toLong(),
                        ""
                    ), episodeVideoRowAdapter
                )
            )
            presenter.loadSeasonEpisodes(lastViewSeasonNumber)

        }


        if (casts.isNotNullOrEmpty()) {
            actionAdapter.add(Action(ACTION_CAST.toLong(), getString(R.string.cast)))
            castRowAdapter.addAll(0, casts)
            mainAdapter.add(
                ListRow(
                    HeaderItem(CAST_HEAD_ID.toLong(), getString(R.string.cast)),
                    castRowAdapter
                )
            )
        }



        if (recommendedWorks.isNotEmpty()) {
            actionAdapter.add(Action(ACTION_RECOMMENDED.toLong(), getString(R.string.recommended)))
            recommendedRowAdapter.setItems(recommendedWorks, workDiffCallback)
            mainAdapter.add(
                ListRow(
                    HeaderItem(
                        RECOMMENDED_HEADER_ID.toLong(),
                        getString(R.string.recommended)
                    ), recommendedRowAdapter
                )
            )
        }

        if (similarWorks.isNotEmpty()) {
            actionAdapter.add(Action(ACTION_SIMILAR.toLong(), getString(R.string.similar)))
            similarRowAdapter.setItems(similarWorks, workDiffCallback)
            mainAdapter.add(
                ListRow(
                    HeaderItem(
                        SIMILAR_HEADER_ID.toLong(),
                        getString(R.string.similar)
                    ), similarRowAdapter
                )
            )
        }

        if (workViewModel.overview == "null" || workViewModel.releaseDate == "null") {
            if (releaseDate == null || releaseDate == "null") workViewModel.releaseDate = ""
            else workViewModel.releaseDate = releaseDate.takeUnless { it.isEmpty() || it.isBlank() }
                    ?.let { releaseDate ->
                        SimpleDateFormat("MMM dd, yyyy", Locale.US).format(
                                SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(releaseDate) as Date
                        )
                    }

            if (overview == null || overview == "null") workViewModel.overview = ""
            else workViewModel.overview = overview

            if(backDropUrl == null || backDropUrl == "null" ) workViewModel.backdropUrl = ""
            else workViewModel.backdropUrl = backDropUrl
            requireActivity().replaceFragment(WorkDetailsFragment.newInstance(workViewModel))
        }

    }

    override fun episodesLoaded(videos: List<VideoViewModel>?) {
        val movies =
            videos?.filter { it.type == "video" || it.type == "stream" }?.sortedBy { it.episodeNumber }
        workViewModel.episodes = movies as java.lang.Object
        episodeVideoRowAdapter.setItems(movies, episodeDiffCallback)
    }

    override fun reviewLoaded(reviews: List<ReviewViewModel>) {
        reviewRowAdapter.setItems(reviews, reviewDiffCallback)
    }

    override fun recommendationLoaded(works: List<WorkViewModel>) {
        recommendedRowAdapter.setItems(works, workDiffCallback)
    }

    override fun similarLoaded(works: List<WorkViewModel>) {
        similarRowAdapter.setItems(works, workDiffCallback)
    }

    override fun errorWorkDetailsLoaded() {
        val fragment = ErrorFragment.newInstance().apply {
            setTargetFragment(this@WorkDetailsFragment, ERROR_FRAGMENT_REQUEST_CODE)
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

    override fun openCastDetails(itemViewHolder: Presenter.ViewHolder, route: Route) {
        val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            (itemViewHolder.view as ImageCardView).mainImageView,
            SettingShared.SHARED_ELEMENT_NAME
        ).toBundle()
        startActivity(route.intent, bundle)
    }

    override fun openVideo(videoViewModel: VideoViewModel) {
        val intent = if (videoViewModel.type == "video" || videoViewModel.type == "stream") {
            Intent(activity, PlayerActivity::class.java).apply {
                putExtra(PlayerActivity.urlKey, videoViewModel.url)
                putExtra(PlayerActivity.movieTitle, videoViewModel.name)
                putExtra(PlayerActivity.isSeriesKey, workViewModel.isSeries)
                putExtra(PlayerActivity.movieId, workViewModel.id)

                if(workViewModel.isSeries != null && workViewModel.isSeries!!)
                {
                    putExtra(PlayerActivity.episodeIdKey, videoViewModel.id)
                }

                putExtra(PlayerActivity.seasonNumberKey, lastViewSeasonNumber)
            }
        } else {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(videoViewModel.url)
            )
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), R.string.failed_open_video, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            ERROR_FRAGMENT_REQUEST_CODE -> {
                requireActivity().popBackStack(
                    ErrorFragment.TAG,
                    androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                when (resultCode) {
                    Activity.RESULT_OK -> presenter.loadData()
                    else -> requireActivity().finish()
                }
            }
        }
    }

    override fun onItemSelected(
        viewHolder: Presenter.ViewHolder?,
        item: Any?,
        rowViewHolder: RowPresenter.ViewHolder?,
        row: Row?
    ) {
        when (row?.run { id.toInt() }) {
            REVIEW_HEADER_ID -> item?.let { presenter.reviewItemSelected(it as ReviewViewModel) }
            RECOMMENDED_HEADER_ID -> item?.let { presenter.recommendationItemSelected(it as WorkViewModel) }
            SIMILAR_HEADER_ID -> item?.let { presenter.similarItemSelected(it as WorkViewModel) }

            EPISODES_HEAD_ID-> item?.let {
                lastViewSeasonNumber = (it as SeasonViewModel).number
                presenter.loadSeasonEpisodes(lastViewSeasonNumber)
            }
        }
    }

    override fun onItemClicked(
        itemViewHolder: Presenter.ViewHolder,
        item: Any,
        rowViewHolder: RowPresenter.ViewHolder,
        row: Row?
    ) {
        when (row?.run { id.toInt() }) {
            CAST_HEAD_ID -> presenter.castClicked(itemViewHolder, item as CastViewModel)
            VIDEO_HEADER_ID -> presenter.videoClicked(item as VideoViewModel)
            RECOMMENDED_HEADER_ID, SIMILAR_HEADER_ID -> presenter.workClicked(
                itemViewHolder,
                item as WorkViewModel
            )
 //           EPISODES_HEAD_ID-> item?.let {
   //             lastViewSeasonNumber = (it as SeasonViewModel).number
    //            presenter.loadSeasonEpisodes(lastViewSeasonNumber)
  //          }
            EPISODES_LIST_ID -> presenter.videoClicked(item as VideoViewModel)

        }
    }

    private fun setupDetailsOverviewRow() {
        detailsOverviewRow = DetailsOverviewRow(workViewModel)
        seasonListRow = DetailsOverviewRow(workViewModel)

        workViewModel.loadPoster(requireContext()) {
            detailsOverviewRow.imageDrawable = it
            mainAdapter.notifyArrayItemRangeChanged(0, mainAdapter.size())
        }

        //TODO The background image that should be converted in a trailer video
        workViewModel.loadBackdrop(requireContext()) {
            coverImage =it
            detailsBackground.coverBitmap = it
            mainAdapter.notifyArrayItemRangeChanged(0, mainAdapter.size())
        }

        detailsOverviewRow.actionsAdapter = actionAdapter
        mainAdapter.add(detailsOverviewRow)

        setOnItemViewSelectedListener(this)
        onItemViewClickedListener = this
    }

    private fun setupDetailsOverviewRowPresenter() {
        val workDetailsDescriptionRender = WorkDetailsDescriptionRender()
        // Set detail background.
        val detailsPresenter =
            object : FullWidthDetailsOverviewRowPresenter(WorkDetailsDescriptionRender()) {

                override fun createRowViewHolder(parent: ViewGroup): RowPresenter.ViewHolder {
                    val viewHolder = super.createRowViewHolder(parent)
                    val detailsImageView =
                        viewHolder.view.findViewById<ImageView>(R.id.details_overview_image)
                    val layoutParams = detailsImageView?.layoutParams?.apply {
                        width = resources.getDimensionPixelSize(R.dimen.movie_width)
                        height = resources.getDimensionPixelSize(R.dimen.movie_height)
                    }
                    detailsImageView?.layoutParams = layoutParams
                    return viewHolder
                }
            }.apply {
                // Hook up transition element.
                val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper().apply {
                    setSharedElementEnterTransition(activity, SettingShared.SHARED_ELEMENT_NAME)
                }

                actionsBackgroundColor = resources.getColor(
                    R.color.detail_view_actionbar_background,
                    requireActivity().theme
                )
                backgroundColor =
                    resources.getColor(R.color.detail_view_background, requireActivity().theme)

                setListener(sharedElementHelper)
                isParticipatingEntranceTransition = true
                setOnActionClickedListener { action ->
                    when (val position = actionAdapter.indexOf(action)) {
                        0 -> presenter.setFavorite()
                        else -> {
                            if (workViewModel.isSeries!=null && workViewModel.isSeries!! && position>2)
                                setSelectedPosition(position+1)
                            else setSelectedPosition(position)
                        }
                    }
                }
            }

        presenterSelector.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)

    }



    private fun playBackVideo( url:String?,cover:String?){
        url?.let {
            val job:CoroutineContext = lifecycleScope.launch {
                supervisorScope {
                    delay(videoPlayBackDelay)
                    player = CustomMediaPlayerGlue(requireContext())
                    player?.let{_player ->
                        _player.setVideoUrl(it)
                        _player.playWhenPrepared()

                        detailsBackground.setupVideoPlayback(_player)
                        mainAdapter.notifyArrayItemRangeChanged(0, mainAdapter.size())
                        delay(30000)
                        _player.pause()

                    }
                }
            }
        }
    }


    companion object {

        fun newInstance(workViewModel: WorkViewModel) =
            WorkDetailsFragment().apply {
                arguments = bundleOf(
                    WORK to workViewModel
                )
            }
    }
}
