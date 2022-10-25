package com.andtv.flicknplay.workbrowse.presentation.presenter;

import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.RowPresenter;

public class CustomPresenter extends ListRowPresenter {

    private final int mInitialSelectedPosition;

    public CustomPresenter(int position) {
        this.mInitialSelectedPosition = position;
    }

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);

        ViewHolder vh = (ListRowPresenter.ViewHolder) holder;
        vh.getGridView().setSelectedPosition(mInitialSelectedPosition);
        vh.getGridView().setNumRows(1);

    }


}