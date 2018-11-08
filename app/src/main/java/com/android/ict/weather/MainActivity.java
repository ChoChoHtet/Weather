package com.android.ict.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ict.weather.Adapter.WeatherAdapter;
import com.android.ict.weather.Retrofit.APIService;
import com.android.ict.weather.Util.Common;
import com.android.ict.weather.model.ConditionList;
import com.android.ict.weather.model.Main;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView list;
    private WeatherAdapter adapter;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    private APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService= Common.getAPI();

        list=findViewById(R.id.weather_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        data();
    }

    private void data() {
       compositeDisposable.add(mService.getConditionList()
       .observeOn(AndroidSchedulers.mainThread())
       .subscribeOn(Schedulers.io())
       .subscribe(new Consumer<Main>() {
           @Override
           public void accept(Main main) throws Exception {
               adapter=new WeatherAdapter(getApplicationContext(),main.getList());
               list.setAdapter(adapter);

           }
       }));
    }


}
