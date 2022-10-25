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

package com.andtv.flicknplay.castdetail.presentation.ui.render

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.andtv.flicknplay.model.presentation.model.CastViewModel
import com.andtv.flicknplay.presentation.extension.hasContent

/**
 *.
 */
private const val SUBTITLE_MAX_LINE = 18

class CastDetailsDescriptionRender : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(viewHolder: ViewHolder, item: Any) {
        val castViewModel = item as CastViewModel
        viewHolder.title.text = castViewModel.name
        castViewModel.source?.let {
            viewHolder.body.text = it
        }
        castViewModel.biography?.takeIf { it.hasContent() }
                ?.let {
                    viewHolder.subtitle.maxLines = SUBTITLE_MAX_LINE
                    viewHolder.subtitle.text = it
                }
    }
}
