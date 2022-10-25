/*
 *
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

package com.andtv.flicknplay.splash.presentation.ui.fragment

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andtv.flicknplay.presentation.extension.finish
import com.andtv.flicknplay.splash.R
import com.andtv.flicknplay.splash.databinding.FragmentSplashBinding

import com.andtv.flicknplay.workbrowse.helper.SharedPreferencesHelper


/**
 *
 */
private const val SPLASH_ANIMATION_FILE = "android.resource://com.andtv.flicknplay/raw/splash_animation"

class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_splash, container, false)
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userData = SharedPreferencesHelper.readLoginForm(requireActivity())
        if(userData==null) {
            binding.animationVideoView.run {
                setOnCompletionListener {
                    requireActivity().finish(Activity.RESULT_OK)
                }
                setVideoURI(
                    Uri.parse(SPLASH_ANIMATION_FILE)
                )
                start()
            }
        }else{
            requireActivity().finish(Activity.RESULT_OK)
        }
    }

    companion object {

        fun newInstance() = SplashFragment()
    }
}
