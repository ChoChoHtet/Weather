package com.android.ict.weather.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ict.weather.Activities.DetailActivity;
import com.android.ict.weather.R;
import com.android.ict.weather.Model.ConditionList;
import com.bumptech.glide.Glide;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder>  {
   private Context context;
   private List<ConditionList>list;

    public WeatherAdapter(Context context) {
        this.context = context;
    }

    public void addList(List<ConditionList> lists){
        this.list=lists;
    }
    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final WeatherHolder holder, int position) {
        ConditionList conditionList=list.get(position);
        holder.tvHumb.setText(conditionList.getCurrentLocation().getHumidity().toString());
        holder.tvWinSpeed.setText(String.valueOf(conditionList.getWind().getSpeed()));
        holder.tvWindDeg.setText(String.valueOf(conditionList.getWind().getDeg())+" ");
        holder.tvDateTime.setText(conditionList.getDateTime());
        holder.tvDesc.setText(conditionList.getWeather().get(0).getDescription());
        holder.tvPress.setText(conditionList.getCurrentLocation().getPressure().toString()+"");
        holder.tvTempMin.setText(String.valueOf(conditionList.getCurrentLocation().getTempMin())+" ");
        holder.tvTempMax.setText(String.valueOf(conditionList.getCurrentLocation().getTempMax()));
        final String iconUrl = "http://openweathermap.org/img/w/" +conditionList.getWeather().get(0).getIcon() + ".png";
        Glide.with(context).load(iconUrl).into(holder.img);

        //button listener
       holder.btnDetail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bundle bundle=new Bundle();
               bundle.putString("humiditiy",holder.tvHumb.getText().toString());
               bundle.putString("pressure",holder.tvPress.getText().toString());
               bundle.putString("description",holder.tvDesc.getText().toString());
               bundle.putString("tempMin",holder.tvTempMin.getText().toString());
               bundle.putString("tempMax",holder.tvTempMax.getText().toString());
               bundle.putString("speed",holder.tvWinSpeed.getText().toString());
               bundle.putString("deg",holder.tvWindDeg.getText().toString());
               bundle.putString("time",holder.tvDateTime.getText().toString());
               bundle.putString("Icon",iconUrl);
               Intent intent=new Intent(v.getContext(), DetailActivity.class);
               intent.putExtras(bundle);
               v.getContext().startActivity(intent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WeatherHolder extends RecyclerView.ViewHolder {
        private TextView tvDateTime, tvDesc, tvTempMin, tvTempMax, tvPress, tvHumb, tvWindDeg, tvWinSpeed;
        private ImageView img;
        private Button btnDetail;

        // public ButtonListener buttonListener;
        public WeatherHolder(View itemView) {
            super(itemView);
            tvDateTime = itemView.findViewById(R.id.tv_date_time);
            tvWinSpeed = itemView.findViewById(R.id.tv_windSpeed);
            tvWindDeg = itemView.findViewById(R.id.tv_windDeg);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvHumb = itemView.findViewById(R.id.tv_humidity);
            tvPress = itemView.findViewById(R.id.tv_press);
            img = itemView.findViewById(R.id.icon);
            tvTempMin = itemView.findViewById(R.id.tv_tempMin);
            tvTempMax = itemView.findViewById(R.id.tv_tempMax);
            btnDetail = itemView.findViewById(R.id.btn_detail);
            // btnDetail.setOnClickListener(this);


        }
      /*  public void setButtonListener(ButtonListener buttonListener){
            this.buttonListener=buttonListener;
        }

        @Override
        public void onClick(View v) {
            buttonListener.buttonClick(v);
          *//*  Bundle bundle=new Bundle();
            bundle.putString("desc",description);
            Intent intent=new Intent(v.getContext(), DetailActivity.class);
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);*//*


        }
    }
    interface ButtonListener{
        public void buttonClick(View v);
    }*/
    }
}
