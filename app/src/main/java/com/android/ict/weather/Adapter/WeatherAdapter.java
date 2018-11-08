package com.android.ict.weather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ict.weather.R;
import com.android.ict.weather.model.ConditionList;
import com.android.ict.weather.model.CurrentLocation;
import com.bumptech.glide.Glide;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder>  {
   private Context context;
   private List<ConditionList>list;

    public WeatherAdapter(Context context, List<ConditionList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        ConditionList conditionList=list.get(position);
        //holder.tvHumb.setText(conditionList.getCurrentLocation().getHumidity());
        holder.tvWinSpeed.setText(String.valueOf(conditionList.getWind().getSpeed()));
        holder.tvWindDeg.setText(String.valueOf(conditionList.getWind().getDeg())+" ");
        holder.tvDateTime.setText(conditionList.getDateTime());
        holder.tvDesc.setText(conditionList.getWeather().get(0).getDescription());
        holder.tvPress.setText(conditionList.getCurrentLocation().getPressure()+"");
        holder.tvTempMin.setText(String.valueOf(conditionList.getCurrentLocation().getTempMin())+" ");
        holder.tvTempMax.setText(String.valueOf(conditionList.getCurrentLocation().getTempMax()));
        String iconUrl = "http://openweathermap.org/img/w/" + conditionList.getWeather().get(0).getIcon()+ ".png";
        Glide.with(context).load(iconUrl).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WeatherHolder extends RecyclerView.ViewHolder{
        private TextView tvDateTime,tvDesc,tvTempMin,tvTempMax,tvPress,tvHumb,tvWindDeg,tvWinSpeed;
        private ImageView img;
        public WeatherHolder(View itemView) {
            super(itemView);
            tvDateTime=itemView.findViewById(R.id.tv_date_time);
            tvWinSpeed=itemView.findViewById(R.id.tv_windSpeed);
            tvWindDeg=itemView.findViewById(R.id.tv_windDeg);
            tvDesc=itemView.findViewById(R.id.tv_desc);
            tvHumb=itemView.findViewById(R.id.tv_humidity);
            tvPress=itemView.findViewById(R.id.tv_press);
            img=itemView.findViewById(R.id.icon);
            tvTempMin=itemView.findViewById(R.id.tv_tempMin);
            tvTempMax=itemView.findViewById(R.id.tv_tempMax);

        }
    }
}
