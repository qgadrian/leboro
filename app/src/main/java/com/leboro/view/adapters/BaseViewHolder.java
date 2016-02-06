package com.leboro.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<ELEMENT_TYPE> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setHolderData(ELEMENT_TYPE element);
}
