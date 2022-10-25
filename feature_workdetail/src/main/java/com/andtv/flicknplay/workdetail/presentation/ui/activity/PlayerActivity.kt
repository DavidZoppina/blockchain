package com.andtv.flicknplay.workdetail.presentation.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.andtv.flicknplay.workdetail.R
import com.andtv.flicknplay.workdetail.di.PlayerActivityComponent
import com.andtv.flicknplay.workdetail.di.PlayerActivityComponentProvider
import com.andtv.flicknplay.workdetail.presentation.ui.fragment.PlayerFragment

class PlayerActivity : AppCompatActivity() {
    companion object {
        const val urlKey = "url"
        const val movieTitle = "title"
        const val movieId = "id"
        const val episodeIdKey = "episodeId"
        const val seekTimeKey = "seekTime"
        const val seasonNumberKey = "season"
        const val isSeriesKey = "series"

    }

    lateinit var fragment: PlayerFragment
    lateinit var playerActivityComponent: PlayerActivityComponent

    override fun onUserInteraction() {
        super.onUserInteraction()
        fragment.onUserInteraction()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        playerActivityComponent = (application as PlayerActivityComponentProvider)
            .playerActivityComponent()
            .also {
                it.inject(this)
            }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        System.out.println("Enteredintotheplayer")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }

        var url:String ? = null
        var movieName:String ? = null
        var id:Int? = null
        var episodeId:Int? = null
        var seekTime: Int?=null
        var seasonNumber: Int? =null
        var isSeries: Boolean = false
        intent?.extras?.let {
            it.getString(movieTitle)?.let {
                movieName = it
            }

            it.getInt(movieId).let {
                id = it
            }

            it.getString(episodeIdKey)?.let {
                episodeId = it.toInt()
            }

            it.getString(urlKey)?.let {
                url = it
            }

            it.getInt(seekTimeKey).let {
                seekTime =it
            }
            it.getInt(seasonNumberKey).let {
                seasonNumber = it
            }
            it.getBoolean(isSeriesKey).let {
                isSeries = it
            }

        }


        fragment = PlayerFragment.newInstance(url, id?.let { it } ?: -1,
            episodeId?.let { it } ?: -1, movieName,
            seekTime, isSeries, seasonNumber?.let{it}?:0)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment ).commit()

    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }


    override fun onBackPressed() {
        fragment.onBackPressed()
    }

    fun superBackPress(){
        super.onBackPressed()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        val b = super.dispatchKeyEvent(event)
        fragment.dispatchKeyEvent(event, b)
        return b
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        fragment.onKeyDown(keyCode, event, super.onKeyDown(keyCode, event))
        return super.onKeyDown(keyCode, event)
    }

}


infix fun Nothing?.void(run: Unit) {

}



