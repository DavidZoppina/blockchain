

package com.andtv.flicknplay.workdetail.presentation.ui.diffcallback

import androidx.leanback.widget.DiffCallback
import com.andtv.flicknplay.workdetail.presentation.model.ReviewViewModel

/**
 *
 */
class ReviewDiffCallback : DiffCallback<ReviewViewModel>() {

    override fun areItemsTheSame(oldItem: ReviewViewModel, newItem: ReviewViewModel) =
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ReviewViewModel, newItem: ReviewViewModel) =
            oldItem == newItem
}
