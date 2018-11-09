package com.android.ict.weather.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ict.weather.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.detail_desc)
    TextView detail_desc;
    @BindView(R.id.detail_icon)
    ImageView img;
    @BindView(R.id.detail_press)
    TextView pressure;
    @BindView(R.id.detail_tempMax)
    TextView tempMax;
    @BindView(R.id.detail_tempMin)
    TextView tempMin;
    @BindView(R.id.detail_windSpeed)
    TextView speed;
    @BindView(R.id.detail_windDeg)
    TextView deg;
    @BindView(R.id.detail_humidity)
    TextView hum;
    @BindView(R.id.detail_time)
    TextView time;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        //get pass data
        bundle = getIntent().getExtras();
        time.setText(bundle.getString("time"));
        detail_desc.setText(bundle.getString("description"));
        hum.setText(bundle.getString("humidity"));
        tempMax.setText(bundle.getString("tempMax"));
        tempMin.setText(bundle.getString("tempMin"));
        deg.setText(bundle.getString("deg"));
        speed.setText(bundle.getString("speed"));
        pressure.setText(bundle.getString("pressure"));
        Glide.with(this).load(bundle.getString("Icon")).into(img);

    }
}
