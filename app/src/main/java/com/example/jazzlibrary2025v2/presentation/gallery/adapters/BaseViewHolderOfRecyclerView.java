package com.example.jazzlibrary2025v2.presentation.gallery.adapters;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolderOfRecyclerView extends RecyclerView.ViewHolder {

    private int mCurrentPosition;

    public BaseViewHolderOfRecyclerView(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}