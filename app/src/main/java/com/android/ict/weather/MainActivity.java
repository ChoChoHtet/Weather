package com.android.ict.weather;

import android.content.Intent;
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
import com.android.ict.weather.Adapter.WeatherAdapter;
import com.android.ict.weather.Model.ConditionList;
import com.android.ict.weather.Model.MainWeather;
import com.android.ict.weather.Retrofit.APIService;
import com.android.ict.weather.Util.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.weather_list)RecyclerView list;
    @BindView(R.id.progressbar)ProgressBar progressBar;
    private WeatherAdapter adapter;
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
        adapter=new WeatherAdapter(this);
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





       // list.setAdapter(adapter);

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
}
