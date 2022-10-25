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

package com.andtv.flicknplay.workbrowse.presentation.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.BackgroundManager
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.di.MainActivityComponent
import com.andtv.flicknplay.workbrowse.di.MainActivityComponentProvider
import com.andtv.flicknplay.workbrowse.presentation.presenter.MainPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.fragment.LoginFragment
import com.andtv.flicknplay.workbrowse.presentation.ui.fragment.RegisterFragment
import com.andtv.flicknplay.workbrowse.presentation.ui.fragment.WhoIsWatchingFragment
import com.andtv.flicknplay.workbrowse.presentation.ui.fragment.WorkBrowseFragment
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import javax.inject.Inject
private const val SPLASH_ACTIVITY_REQUEST_CODE = 1

/**
 * Copyright (C) Flicknplay 11-02-2018.
 */
class MainActivity : FragmentActivity() {

    private val backgroundManager: BackgroundManager by lazy { BackgroundManager.getInstance(this) }
    lateinit var mainActivityComponent: MainActivityComponent

    @Inject
    lateinit var presenter: MainPresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityComponent = (application as MainActivityComponentProvider)
                .mainActivityComponent()
                .also {
                    it.inject(this)
                }
        super.onCreate(savedInstanceState)

        backgroundManager.attach(window)
        backgroundManager.setBitmap(null)

        presenter.bindTo(lifecycle)
        presenter.loadRecommendations()

        when (savedInstanceState) {
            null -> startActivityForResult(
                    presenter.getSplashRoute().intent,
                    SPLASH_ACTIVITY_REQUEST_CODE
            )
            else -> replaceFragment(LoginFragment.newInstance())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SPLASH_ACTIVITY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    replaceFragment(LoginFragment.newInstance())
                } else {
                    finish()
                }
            }
        }
    }

    companion object{
        const val RC_SIGN_IN = 1001
    }

    override fun onDestroy() {
        backgroundManager.release()
        super.onDestroy()
    }
}
