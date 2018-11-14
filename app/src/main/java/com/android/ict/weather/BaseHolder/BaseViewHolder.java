package com.android.ict.weather.BaseHolder;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.ict.weather.Model.ConditionList;


public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T data,BaseViewHolder.onClick onClickListener);

    public interface onClick{
         void onItemClick(int position);
         void onClick(ConditionList list);
    }
}

