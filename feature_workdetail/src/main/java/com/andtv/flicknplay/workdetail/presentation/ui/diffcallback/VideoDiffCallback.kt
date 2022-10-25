package com.andtv.flicknplay.workdetail.presentation.ui.diffcallback

import androidx.leanback.widget.DiffCallback
import com.andtv.flicknplay.workdetail.presentation.model.VideoViewModel


class VideoDiffCallback : DiffCallback<VideoViewModel>() {
    override fun areItemsTheSame(oldItem: VideoViewModel, newItem: VideoViewModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: VideoViewModel, newItem: VideoViewModel) =
        oldItem.id == newItem.id
}