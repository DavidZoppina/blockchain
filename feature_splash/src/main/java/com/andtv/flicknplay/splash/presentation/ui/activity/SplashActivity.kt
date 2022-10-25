

package com.andtv.flicknplay.splash.presentation.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.splash.presentation.ui.fragment.SplashFragment

/**
 *
 */
class SplashActivity : FragmentActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(SplashFragment.newInstance())
    }
}
