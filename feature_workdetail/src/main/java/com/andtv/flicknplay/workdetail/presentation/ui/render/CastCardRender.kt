

package com.andtv.flicknplay.workdetail.presentation.ui.render

import android.view.ViewGroup
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.andtv.flicknplay.model.presentation.model.CastViewModel
import com.andtv.flicknplay.presentation.extension.loadImageInto
import com.andtv.flicknplay.workdetail.R

/**
 *
 */
class CastCardRender : Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
            ViewHolder(ImageCardView(parent.context).apply {
                isFocusable = true
                isFocusableInTouchMode = true
            })

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val castViewModel = item as CastViewModel
        val cardView = viewHolder.view as ImageCardView
        cardView.titleText = castViewModel.name
        cardView.contentText = castViewModel.character
        cardView.setMainImageDimensions(viewHolder.view.context.resources.getDimensionPixelSize(R.dimen.character_image_card_width),
                viewHolder.view.context.resources.getDimensionPixelSize(R.dimen.character_image_card_height))

        castViewModel.thumbnailUrl?.let {
            cardView.mainImageView.loadImageInto(it)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as ImageCardView
        cardView.badgeImage = null
        cardView.mainImage = null
    }
}
