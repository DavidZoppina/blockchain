package com.andtv.flicknplay.workdetail.presentation.ui.fragment

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.andtv.flicknplay.presentation.extension.isNotNullOrEmpty
import com.andtv.flicknplay.workdetail.R
import com.andtv.flicknplay.workdetail.presentation.manager.CenterLayoutManager
import com.andtv.flicknplay.workdetail.presentation.model.VideoViewModel
import com.andtv.flicknplay.workdetail.presentation.presenter.PlayerFragmentPresenter
import com.andtv.flicknplay.workdetail.presentation.ui.activity.PlayerActivity
import com.andtv.flicknplay.workdetail.presentation.ui.activity.void
import com.andtv.flicknplay.workdetail.presentation.ui.adapter.EpisodesAdapter
import javax.inject.Inject

private const val inactivityTimeOutInMilieus = 4*60*60*1000L
private const val updateVideoHistoryTime = 2000L
private const val urlKey = "url"
private const val movieTitleKey = "title"
private const val movieIdKey = "id"
private const val episodeIdKey = "episodeId"
private const val seekTimeKey = "seekTime"
private const val seriesKey = "series"
private const val seasonKey = "season"

class PlayerFragment : Fragment(), PlayerFragmentPresenter.View  {

    private var playerView: StyledPlayerView? = null
    private var videoPlayer: ExoPlayer? = null
    private var btnEpisodes: Button? = null
    private var rvEpisodes: RecyclerView? = null
    private var episodesAdapter: EpisodesAdapter? = null
    private var selectedTrackIndex = 0

    private var stillWatchingLayout: ConstraintLayout? = null
    private var btnContinueWatching: Button? = null
    private var btnBack: Button? = null
    private var stillWatchingTV: TextView? = null
    private var episodesList : List<VideoViewModel>? = null

    private val movieName by lazy { arguments?.getString(movieTitleKey) }
    private val movieId by lazy { arguments?.getInt(movieIdKey) }
    private val movieUrl by lazy { arguments?.getString(urlKey) }
    private val seekTime by lazy{ arguments?.getInt(seekTimeKey)}
    private val isSeries by lazy{ arguments?.getBoolean(seriesKey)}
    private val seasonNumber by lazy{ arguments?.getInt(seasonKey)}
    private val episodeId by lazy{ arguments?.getInt(episodeIdKey)}

    private var diloagCounter:Long = 0L

    @Inject
    lateinit var presenter: PlayerFragmentPresenter

    var handler: Handler? =  Handler(Looper.getMainLooper())
    val stillWatchinRunnable:Runnable? = Runnable {
        stillWatchingLayout?.visibility = View.VISIBLE
        videoPlayer?.pause()
        btnEpisodes?.visibility = View.GONE
        btnEpisodes?.isEnabled = false
    }

    var updateHistoryHandler: Handler? =  Handler(Looper.getMainLooper())
    val updateHistoryRunnable:Runnable? = Runnable {

        val currentSeekTime = (videoPlayer?.let { it.currentPosition.toInt() / 1000 } ?: 0)
        val duration = (videoPlayer?.let { it.duration.toInt() / 1000 } ?: 0)
        if(isSeries!= null && isSeries!! == true)
        {
            // if ID will null it will send update
            episodeId?.let{
                if(it != -1) {
                    presenter.updateVideoHistory(it,
                        currentSeekTime, duration
                    )
                }
            }
        }else{
            // if ID will null it will send update
            movieId?.let{
                if(it != -1) {
                    presenter.updateVideoHistory(it,
                        currentSeekTime, duration
                    )
                }
            }
        }



    }

    fun resetUpdateHistoryRunnable ()  =   updateHistoryRunnable?.let{
        updateHistoryHandler?.removeCallbacks(it)
        updateHistoryHandler?.postDelayed(it, updateVideoHistoryTime)
    }


    fun updateHistoryJob(){
    }

    var isIntractivityCallbackSet = false
    var isUpdateHistoryCallbackSet = false


    override fun onAttach(context: Context) {
        (requireActivity() as PlayerActivity).playerActivityComponent
            .playerFragmentComponent()
            .create(this)
            .inject(this)

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.bindTo(this.lifecycle)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        playerView = view.findViewById(R.id.player_view)
        btnEpisodes = view.findViewById(R.id.btEpisodes)
        rvEpisodes = view.findViewById(R.id.rvEpisodes)
        stillWatchingLayout = view.findViewById(R.id.stillWatchingLayout)
        stillWatchingTV = view.findViewById(R.id.stillWatchingLabel)
        btnContinueWatching = view.findViewById(R.id.btnContinueWatching)
        btnBack = view.findViewById(R.id.btnBack)

        rvEpisodes?.layoutManager = CenterLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)


        return view
    }


    private fun initStillWatchingListener(){

        stillWatchingTV?.text = "Are you still watching \"${movieName}\"?"

        btnBack?.setOnClickListener{
            requireActivity().onBackPressed()
        }

        btnContinueWatching?.setOnClickListener {
            isIntractivityCallbackSet = false
            stillWatchingLayout?.visibility = View.GONE
            videoPlayer?.play()
        }
    }

    private fun initPlayer() {
        episodesList?.let{
            episodesAdapter = EpisodesAdapter(episodesList, object: EpisodesAdapter.EpisodeSelected{
                override fun onEpisodesSelected(view: View?, position: Int?) {
                    position?.let {
                        if(selectedTrackIndex != position) {
                            selectedTrackIndex = position
                            videoPlayer?.seekToDefaultPosition(position)
                            episodesAdapter?.selectionEpisode = position
                        }
                    }
                }

            })
        }




        playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        videoPlayer = ExoPlayer.Builder(requireContext()).build()

        if (episodesList.isNullOrEmpty()) {
            movieUrl?.let {
                videoPlayer?.setMediaItem(MediaItem.fromUri(it))
            }
        } else {
            episodesList?.forEachIndexed { index, episode ->
                episode.url?.let { url ->
                    videoPlayer?.addMediaItem(MediaItem.fromUri(url))
                    if (movieUrl != null && movieUrl!! == url) {
                        selectedTrackIndex = index
                        episodesAdapter?.selectionEpisode = index
                        videoPlayer?.seekToDefaultPosition(index)

                    }

                }
                if (!isIntractivityCallbackSet) {
                    stillWatchinRunnable?.let {
                        isIntractivityCallbackSet = true
                        handler?.postDelayed(stillWatchinRunnable, inactivityTimeOutInMilieus)
                    }
                }

            }
            rvEpisodes?.adapter = episodesAdapter

            btnEpisodes?.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    rvEpisodes?.visibility = View.VISIBLE
                    episodesAdapter?.selectionEpisode = selectedTrackIndex
                    episodesAdapter?.notifyDataSetChanged()
                    rvEpisodes?.smoothScrollToPosition(selectedTrackIndex)
                    //                        rvEpisodes?.requestFocus()
                    v?.visibility = View.GONE
                }
            })
        }

        initStillWatchingListener()

        videoPlayer?.prepare()
        seekTime?.let { videoPlayer?.seekTo(it.toLong()*1000)}
        videoPlayer?.playWhenReady = true
        playerView?.player = videoPlayer
        playerView?.requestFocus()
        playerView?.setControllerVisibilityListener(object :
            StyledPlayerControlView.VisibilityListener {
            override fun onVisibilityChange(visibility: Int) {
                when (visibility) {
                    View.VISIBLE -> {
                        if (episodesList.isNotNullOrEmpty() && rvEpisodes?.visibility != View.VISIBLE) {
                            if(stillWatchingLayout?.visibility == View.GONE){
                                btnEpisodes?.visibility = View.VISIBLE
                                btnEpisodes?.isEnabled = true
                            }
                        }
                    }
                    View.GONE -> {
                        btnEpisodes?.visibility = View.GONE
                        btnEpisodes?.isEnabled = false

                    }
                }
            }
        })

        videoPlayer?.addListener(object : Player.Listener{
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)

                if(isPlaying){
                    if (!isIntractivityCallbackSet) {
                        stillWatchinRunnable?.let {
                            isIntractivityCallbackSet = true
                            handler?.postDelayed(stillWatchinRunnable, inactivityTimeOutInMilieus)
                        }
                    }
                }
                else{

                }
            }

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                super.onPositionDiscontinuity(oldPosition, newPosition, reason)

                if(oldPosition.mediaItemIndex != newPosition.mediaItemIndex) {
                    selectedTrackIndex = newPosition.mediaItemIndex
                    episodesAdapter?.selectionEpisode = selectedTrackIndex
                    episodesAdapter?.notifyDataSetChanged()
                    rvEpisodes?.smoothScrollToPosition(selectedTrackIndex)
                }
            }
        })

        if(!isUpdateHistoryCallbackSet)
        {
            isUpdateHistoryCallbackSet = true
            updateHistoryRunnable?.let{updateHistoryHandler?.postDelayed(updateHistoryRunnable, updateVideoHistoryTime)}
        }

    }

    fun onUserInteraction(){
        stillWatchinRunnable?.let { runnable ->
            handler?.let { handler ->
               if(episodesList.isNotNullOrEmpty()) {
                   if (isIntractivityCallbackSet)
                       handler.removeCallbacks(runnable)
                   handler.postDelayed(runnable, inactivityTimeOutInMilieus)
               }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(isSeries == true && movieId!=null && seasonNumber!=null){
            presenter.loadSeasonEpisodes(movieId!!, seasonNumber!!)
        }else{
            initPlayer()
        }

    }

    override fun onPause() {
        super.onPause()
        if(videoPlayer != null && videoPlayer?.isPlaying == true) videoPlayer?.pause()
    }

    override fun onStop() {
        super.onStop()
        if (videoPlayer != null) {
            videoPlayer?.stop()
            videoPlayer?.release()
            videoPlayer = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoPlayer?.stop()
        videoPlayer?.release()
        videoPlayer = null
    }

    fun onBackPressed() {

        stillWatchinRunnable?.let { runnable ->
            handler?.let {
                    handler ->
                handler.removeCallbacks(runnable)
            }

            updateHistoryRunnable?.let { runnable ->
                updateHistoryHandler?.let { handler ->
                    handler.removeCallbacks(runnable)
                }
            }
        }
        if(rvEpisodes?.visibility == View.VISIBLE)
        {
            rvEpisodes?.visibility = View.GONE
        }
        else {
            (requireActivity() as PlayerActivity).superBackPress()
            if (videoPlayer?.isPlaying == true) {

                val am = requireActivity().getSystemService(AppCompatActivity.AUDIO_SERVICE) as AudioManager
                am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)

                videoPlayer?.stop()
                videoPlayer?.release()
                videoPlayer?.release()
                videoPlayer = null

                ExoPlayer.COMMAND_STOP

                var public: Nothing? = null
                public void run {
                    if (Thread.interrupted()) {
                        return
                    }
                }
            }
        }
    }

    fun dispatchKeyEvent(event: KeyEvent?, b: Boolean): Boolean {
        if (event != null) {
            return playerView!!.dispatchKeyEvent(event).or(b)
        }
        return b
    }

    fun onKeyDown(keyCode: Int, event: KeyEvent?, b: Boolean) {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER -> showController(b)
            KeyEvent.KEYCODE_DPAD_DOWN -> showController(b)
            KeyEvent.KEYCODE_DPAD_RIGHT -> showController(b)
            KeyEvent.KEYCODE_DPAD_LEFT -> showController(b)
            KeyEvent.KEYCODE_DPAD_UP_LEFT -> showController(b)
            KeyEvent.KEYCODE_DPAD_DOWN_LEFT -> showController(b)
            KeyEvent.KEYCODE_DPAD_UP_RIGHT -> showController(b)
            KeyEvent.KEYCODE_DPAD_DOWN_RIGHT -> showController(b)
        }
    }

    private fun showController(b: Boolean): Boolean {
        if (playerView?.isControllerFullyVisible == false) {
            playerView?.showController()
        }
        return b
    }

    override fun historyUpdated() {
        try{resetUpdateHistoryRunnable()} catch (e: Exception) { }

    }

    override fun videoUpdateFailed() {
        try{resetUpdateHistoryRunnable()} catch (e: Exception) { }
    }

    override fun onEpisodesLoaded(result: List<VideoViewModel>) {
        episodesList = result
        initPlayer()
    }

    override fun onEpisodesLoadingFailed() {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    companion object {
        @JvmStatic
        fun newInstance(url: String?, id: Int, episodeId:Int?, movieName: String?,
                        seekTime: Int?, isSeries:Boolean, seasonNumber: Int):PlayerFragment {

            return PlayerFragment().apply {
                arguments = bundleOf(
                    urlKey to url,
                    movieIdKey to id,
                    episodeIdKey to episodeId,
                    movieTitleKey to movieName,
                    seekTimeKey to seekTime,
                    seriesKey to isSeries,
                    seasonKey to seasonNumber

                )
            }
        }
    }

}