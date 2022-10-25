

package com.andtv.flicknplay.workdetail.presentation.ui.render

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import com.andtv.flicknplay.model.presentation.model.WorkViewModel

/**
 *
 */
private const val SUBTITLE_MAX_LINE = 15

class WorkDetailsDescriptionRender : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(viewHolder: ViewHolder, item: Any) {
        val workViewModel = item as WorkViewModel
        with(viewHolder) {
            title.text = workViewModel.title
            subtitle.maxLines = SUBTITLE_MAX_LINE
            subtitle.text = "${workViewModel.releaseDate}\n\n${workViewModel.overview}"
            subtitle
            body.text = workViewModel.source
        }
    }
}
