package com.android.ict.weather.Views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ict.weather.BaseHolder.BaseViewHolder;
import com.android.ict.weather.Model.ConditionList;
import com.android.ict.weather.R;
import com.bumptech.glide.Glide;

public class WeatherView extends BaseViewHolder<ConditionList> {
    private TextView tvDateTime, tvDesc, tvTempMin, tvTempMax, tvPress, tvHumb, tvWindDeg, tvWinSpeed;
    private ImageView img;
    public Button btnDetail;

    public WeatherView(View itemView) {
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

    }


    @Override
    public void bind(final ConditionList data, final BaseViewHolder.onClick onClickListener) {
        tvDateTime.setText(data.getDateTime());
        tvDesc.setText(data.getWeather().get(0).getDescription());
        tvHumb.setText(String.valueOf(data.getCurrentLocation().getHumidity()));
        tvPress.setText(String.valueOf(data.getCurrentLocation().getPressure()));
        tvTempMax.setText(String.valueOf(data.getCurrentLocation().getTempMax()));
        tvTempMin.setText(String.valueOf(data.getCurrentLocation().getTempMin()));
        tvWindDeg.setText(String.valueOf(data.getWind().getDeg()));
        tvWinSpeed.setText(String.valueOf(data.getWind().getSpeed()));

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(data);

            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        });
        Glide.with(itemView.getContext())
                .load("http://openweathermap.org/img/w/" + data.getWeather().get(0).getIcon()+".png")
                .into(img);

    }


}
