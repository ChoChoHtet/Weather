package com.android.ict.weather;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.MainThread;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.ict.weather.Activities.CityActivity;
import com.android.ict.weather.Activities.DetailActivity;
import com.android.ict.weather.Adapter.WeatherAdapter;
import com.android.ict.weather.Adapter.WeatherAdapter1;
import com.android.ict.weather.BaseHolder.BaseViewHolder;
import com.android.ict.weather.Model.ConditionList;
import com.android.ict.weather.Model.MainWeather;
import com.android.ict.weather.Retrofit.APIService;
import com.android.ict.weather.Util.Common;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements BaseViewHolder.onClick{
    @BindView(R.id.weather_list)RecyclerView list;
    @BindView(R.id.progressbar)ProgressBar progressBar;
    private WeatherAdapter1 adapter;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private APIService mService;
    private LinearLayout linearLayout;

    @BindView(R.id.btn_city)Button btnCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=findViewById(R.id.weather_list);
        linearLayout=findViewById(R.id.linear_layout);
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        adapter=new WeatherAdapter1(this);
        adapter.setOnClickListener(MainActivity.this);
        ButterKnife.bind(this);

        mService= Common.getAPI();
        if(!Connection.checkConnection(this)){
            Snackbar snackbar=Snackbar.make(linearLayout,"No Internet Connection!",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Try Again", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(Connection.checkConnection(v.getContext())){
                                data();
                            }else{
                                tryAgain();
                            }
                        }
                    });
            snackbar.show();

        }else {
            data();
            btnCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(), CityActivity.class));

                }
            });
        }






    }

    private void data() {
       compositeDisposable.add(mService.getConditionList()
       .observeOn(AndroidSchedulers.mainThread())
       .subscribeOn(Schedulers.io())
       .subscribe(new Consumer<MainWeather>() {
           @Override
           public void accept(MainWeather mainWeather) throws Exception {
               progressBar.setVisibility(View.GONE);
               adapter.addList(mainWeather.getList());
               adapter.notifyDataSetChanged();
               //WeatherAdapter1 adapter=new WeatherAdapter1(getApplicationContext(),mainWeather.getList());
               list.setAdapter(adapter);

           }
       }));
    }
    private void tryAgain(){
        Snackbar snackbar=Snackbar.make(linearLayout,"No Internet Connection!",Snackbar.LENGTH_INDEFINITE)
                .setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Connection.checkConnection(v.getContext())){
                            data();
                        }
                    }
                });
        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(compositeDisposable!=null && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getApplicationContext(),"Position: "+position+" is Clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(ConditionList list) {
        Bundle bundle=new Bundle();
        bundle.putString("humiditiy",String.valueOf(list.getCurrentLocation().getHumidity()));
        bundle.putString("pressure",String.valueOf(list.getCurrentLocation().getPressure()));
        bundle.putString("description",list.getWeather().get(0).getDescription());
        bundle.putString("tempMin",String.valueOf(list.getCurrentLocation().getTempMin()));
        bundle.putString("tempMax",String.valueOf(list.getCurrentLocation().getTempMax()));
        bundle.putString("speed",String.valueOf(list.getWind().getSpeed()));
        bundle.putString("deg",String.valueOf(list.getWind().getDeg()));
        bundle.putString("time",list.getDateTime());
        bundle.putString("Icon", "http://openweathermap.org/img/w/" +list.getWeather().get(0).getIcon() + ".png");
        Intent intent=new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtras(bundle);
       startActivity(intent);
    }

}
