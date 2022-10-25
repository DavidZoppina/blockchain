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

package com.andtv.flicknplay.workdetail.presentation.ui.render

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.Presenter
import com.andtv.flicknplay.workdetail.R
import com.andtv.flicknplay.workdetail.databinding.TextIconCardBinding
import com.andtv.flicknplay.workdetail.presentation.model.ReviewViewModel


/**
 * Copyright (C) Flicknplay 22-04-2020.
 */
class ReviewCardRender : Presenter() {

    lateinit var contentTextView:TextView
    lateinit var authorTextView:TextView

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
            ViewHolder(TextCardView(parent.context).apply {
                isFocusable = true
            })

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val reviewViewModel = item as ReviewViewModel
        val cardView = viewHolder.view as TextCardView

        contentTextView = viewHolder.view.findViewById(R.id.contentTextView)
        authorTextView = viewHolder.view.findViewById(R.id.authorTextView)

        with(cardView) {
            contentTextView.text = reviewViewModel.content
            authorTextView.text = reviewViewModel.author
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as TextCardView
        with(cardView) {
            contentTextView.text = null
            authorTextView.text = null
        }
    }

    private class TextCardView(context: Context?) : BaseCardView(context) {
        init {
           LayoutInflater.from(getContext()).inflate(R.layout.text_icon_card, this)
        }
    }
}
