

package com.andtv.flicknplay.castdetail.presentation.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.andtv.flicknplay.castdetail.di.CastDetailsActivityComponent
import com.andtv.flicknplay.castdetail.di.CastDetailsActivityComponentProvider
import com.andtv.flicknplay.castdetail.presentation.ui.fragment.CastDetailsFragment
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.route.castdetail.CastDetailsRoute
import javax.inject.Inject

/**
 *
 */
class CastDetailsActivity : FragmentActivity() {

    lateinit var castDetailsActivityComponent: CastDetailsActivityComponent

    @Inject
    lateinit var castDetailsRoute: CastDetailsRoute

    public override fun onCreate(savedInstanceState: Bundle?) {
        castDetailsActivityComponent = (application as CastDetailsActivityComponentProvider)
                .castDetailsActivityComponent()
                .also {
                    it.inject(this)
                }
        super.onCreate(savedInstanceState)

        when (val castViewModel = castDetailsRoute.getCastDetailDeepLink(intent)) {
            null -> finish()
            else -> replaceFragment(CastDetailsFragment.newInstance(castViewModel))
        }
    }
}
