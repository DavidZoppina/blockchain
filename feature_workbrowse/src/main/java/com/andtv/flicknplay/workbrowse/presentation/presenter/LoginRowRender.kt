package com.andtv.flicknplay.workbrowse.presentation.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.widget.RowPresenter
import com.andtv.flicknplay.workbrowse.R


class LoginRowRender : RowPresenter() {
    override fun createRowViewHolder(parent: ViewGroup?): ViewHolder {

        val v: View = LayoutInflater.from(parent?.context).inflate(
            R.layout.fragment_login,
            parent,
            false
        )

        val lp: ViewGroup.LayoutParams = v.layoutParams
        lp.width = parent!!.width
        lp.height = parent.height
        v.layoutParams = lp

        return ViewHolder(v)
    }
}