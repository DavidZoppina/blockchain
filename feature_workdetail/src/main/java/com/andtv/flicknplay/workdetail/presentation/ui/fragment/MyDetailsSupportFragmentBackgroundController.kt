package com.andtv.flicknplay.workdetail.presentation.ui.fragment

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackGlueHost
import androidx.leanback.widget.ParallaxTarget

class MyDetailsSupportFragmentBackgroundController(fragment: DetailsSupportFragment?) : DetailsSupportFragmentBackgroundController(
    fragment
) {


    override fun onCreateVideoSupportFragment(): Fragment {
        return MyVideoSupportFragment()


    }
}