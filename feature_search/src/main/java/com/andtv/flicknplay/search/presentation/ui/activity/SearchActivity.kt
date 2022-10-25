

package com.andtv.flicknplay.search.presentation.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.BackgroundManager
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.search.di.SearchActivityComponent
import com.andtv.flicknplay.search.di.SearchActivityComponentProvider
import com.andtv.flicknplay.search.presentation.ui.fragment.SearchFragment

/**
 *
 */
class SearchActivity : FragmentActivity() {

    private val backgroundManager: BackgroundManager by lazy { BackgroundManager.getInstance(this) }

    lateinit var searchActivityComponent: SearchActivityComponent

    public override fun onCreate(savedInstanceState: Bundle?) {
        searchActivityComponent = (application as SearchActivityComponentProvider)
            .searchActivityComponent()
        super.onCreate(savedInstanceState)

        backgroundManager.attach(window)
        backgroundManager.setBitmap(null)

        replaceFragment(SearchFragment.newInstance())
    }

    override fun onDestroy() {
        backgroundManager.release()
        super.onDestroy()
    }
}
