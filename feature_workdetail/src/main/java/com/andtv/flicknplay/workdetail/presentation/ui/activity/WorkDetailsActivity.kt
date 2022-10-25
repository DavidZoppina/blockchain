

package com.andtv.flicknplay.workdetail.presentation.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.route.workdetail.WorkDetailsRoute
import com.andtv.flicknplay.workdetail.di.WorkDetailsActivityComponent
import com.andtv.flicknplay.workdetail.di.WorkDetailsActivityComponentProvider
import com.andtv.flicknplay.workdetail.presentation.ui.fragment.WorkDetailsFragment
import javax.inject.Inject

/**
 *
 */
class WorkDetailsActivity : FragmentActivity() {

    lateinit var workDetailsActivityComponent: WorkDetailsActivityComponent

    @Inject
    lateinit var workDetailsRoute: WorkDetailsRoute

    public override fun onCreate(savedInstanceState: Bundle?) {
        workDetailsActivityComponent = (application as WorkDetailsActivityComponentProvider)
                .workDetailsActivityComponent()
                .also {
                    it.inject(this)
                }
        super.onCreate(savedInstanceState)

        when (val workViewModel = workDetailsRoute.getWorkDetail(intent)) {
            null -> finish()
            else -> replaceFragment(WorkDetailsFragment.newInstance(workViewModel))
        }
    }
}
