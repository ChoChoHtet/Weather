package com.android.ict.weather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.ict.weather.BaseHolder.BaseViewHolder;
import com.android.ict.weather.Model.ConditionList;
import com.android.ict.weather.R;
import com.android.ict.weather.Views.WeatherView;

import java.util.List;

public class WeatherAdapter1 extends RecyclerView.Adapter<WeatherView> {
    private List<ConditionList> list;
    private Context context;
    private BaseViewHolder.onClick onclicklistner;

   /* public WeatherAdapter1(Context context,List<ConditionList> list ) {
        this.context = context;
        this.list = list;
    }*/
   public WeatherAdapter1(Context context){
       this.context = context;
   }
    public void addList(List<ConditionList> lists){
        this.list=lists;
    }

    public void setOnClickListener(BaseViewHolder.onClick listener) {
        this.onclicklistner = listener;
    }

    @NonNull
    @Override
    public WeatherView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherView(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherView holder, final int position) {
        holder.bind(list.get(position),onclicklistner);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
