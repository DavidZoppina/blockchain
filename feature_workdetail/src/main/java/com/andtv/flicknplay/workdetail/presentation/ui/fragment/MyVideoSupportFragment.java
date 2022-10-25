package com.andtv.flicknplay.workdetail.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.leanback.R;
import androidx.leanback.app.PlaybackSupportFragment;
import androidx.leanback.app.VideoSupportFragment;

public class MyVideoSupportFragment extends VideoSupportFragment {
    static final int SURFACE_NOT_CREATED = 0;
    static final int SURFACE_CREATED = 1;

    SurfaceView mVideoSurface;
    SurfaceHolder.Callback mMediaPlaybackCallback;

    int mState = SURFACE_NOT_CREATED;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setPadding(0,10,0,0);
        layout.setOrientation(LinearLayout.VERTICAL);


        int actionsBackgroundColor = getResources().getColor(
                com.andtv.flicknplay.workdetail.R.color.detail_view_actionbar_background,
                requireActivity().getTheme()
        );
        layout.setBackgroundColor(actionsBackgroundColor);

        LinearLayout top = new LinearLayout(requireContext());
        android.view.ViewGroup.LayoutParams pram = new android.view.ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,20);
        top.setLayoutParams(pram);
        layout.addView(top);


        mVideoSurface = (SurfaceView) LayoutInflater.from(getContext()).inflate(
                R.layout.lb_video_surface,
                null, false);

        layout.addView(mVideoSurface);

        root.addView(layout);


        android.view.ViewGroup.LayoutParams videoParams = mVideoSurface.getLayoutParams();
        videoParams.height =requireActivity().getWindowManager().getDefaultDisplay().getHeight()/2;
        mVideoSurface.setLayoutParams(videoParams);

        mVideoSurface.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mMediaPlaybackCallback != null) {
                    mMediaPlaybackCallback.surfaceCreated(holder);
                }
                mState = SURFACE_CREATED;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (mMediaPlaybackCallback != null) {
                    mMediaPlaybackCallback.surfaceChanged(holder, format, width, height);
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mMediaPlaybackCallback != null) {
                    mMediaPlaybackCallback.surfaceDestroyed(holder);
                }
                mState = SURFACE_NOT_CREATED;
            }
        });
        setBackgroundType(PlaybackSupportFragment.BG_LIGHT);
        return root;
    }

    /**
     * Adds {@link SurfaceHolder.Callback} to {@link android.view.SurfaceView}.
     */
    public void setSurfaceHolderCallback(SurfaceHolder.Callback callback) {
        mMediaPlaybackCallback = callback;

        if (callback != null) {
            if (mState == SURFACE_CREATED) {
                mMediaPlaybackCallback.surfaceCreated(mVideoSurface.getHolder());
            }
        }
    }

    @Override
    protected void onVideoSizeChanged(int width, int height) {
        int screenWidth = getView().getWidth();
        int screenHeight = getView().getHeight();

        ViewGroup.LayoutParams p = mVideoSurface.getLayoutParams();
        if (screenWidth * height > width * screenHeight) {
            // fit in screen height
            p.height = screenHeight;
            p.width = screenHeight * width / height;
        } else {
            // fit in screen width
            p.width = screenWidth;
            p.height = screenWidth * height / width;
        }
        mVideoSurface.setLayoutParams(p);
    }

    /**
     * Returns the surface view.
     */
    public SurfaceView getSurfaceView() {
        return mVideoSurface;
    }

    @Override
    public void onDestroyView() {
        mVideoSurface = null;
        mState = SURFACE_NOT_CREATED;
        super.onDestroyView();
    }
}
