package com.andtv.flicknplay.workdetail.presentation.ui.render

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.Presenter
import com.andtv.flicknplay.workdetail.R
import com.andtv.flicknplay.workdetail.presentation.model.SeasonViewModel


class SeasonCardRender: Presenter() {

    lateinit var title:TextView

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder  =
        ViewHolder(TextCardView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
        })


    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any?) {
        val seasonViewModel = item as SeasonViewModel
        val cardView = viewHolder.view as TextCardView

        title = viewHolder.view.findViewById(R.id.title)
        with(cardView)
        {title.text = seasonViewModel.title
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as TextCardView
        with(cardView) {
            title.text = null
        }    }


    private class TextCardView(context: Context?) : BaseCardView(context) {
        init {
            LayoutInflater.from(getContext()).inflate(R.layout.season_number_card, this)
        }
    }
}