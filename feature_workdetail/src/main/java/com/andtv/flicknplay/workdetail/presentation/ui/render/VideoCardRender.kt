

package com.andtv.flicknplay.workdetail.presentation.ui.render

import android.view.ViewGroup
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.andtv.flicknplay.presentation.extension.loadImageInto
import com.andtv.flicknplay.workdetail.R
import com.andtv.flicknplay.workdetail.presentation.model.VideoViewModel

/**
 *
 */
class VideoCardRender : Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
            ViewHolder(ImageCardView(parent.context).apply {
                isFocusable = true
                isFocusableInTouchMode = true
            })

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val videoViewModel = item as VideoViewModel
        val cardView = viewHolder.view as ImageCardView
        cardView.titleText = videoViewModel.name
        cardView.contentText = videoViewModel.type
        cardView.setMainImageDimensions(viewHolder.view.context.resources.getDimensionPixelSize(R.dimen.video_card_width),
                viewHolder.view.context.resources.getDimensionPixelSize(R.dimen.video_card_height))

        videoViewModel.thumbnail?.let {
            cardView.mainImageView.loadImageInto(it)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as ImageCardView
        cardView.badgeImage = null
        cardView.mainImage = null
    }
}
