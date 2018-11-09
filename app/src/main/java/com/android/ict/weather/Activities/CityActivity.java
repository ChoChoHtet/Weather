package com.android.ict.weather.Activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.ict.weather.Adapter.WeatherAdapter;
import com.android.ict.weather.Connection;
import com.android.ict.weather.Model.ConditionList;
import com.android.ict.weather.Model.MainWeather;
import com.android.ict.weather.R;
import com.android.ict.weather.Retrofit.APIService;
import com.android.ict.weather.Util.Common;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CityActivity extends AppCompatActivity {
    @BindView(R.id.ed_city)
    EditText edCity;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.list_city)
    RecyclerView list_city;

    private APIService cityService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private WeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        list_city.setLayoutManager(new LinearLayoutManager(this));
        list_city.setHasFixedSize(true);
        adapter = new WeatherAdapter(this);

        cityService = Common.getAPI();
        if (!Connection.checkConnection(this)) {
            Snackbar snackbar = Snackbar.make(layout, "No Internet Connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Try Again", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Connection.checkConnection(v.getContext())) {
                                //data();
                            } else {
                                tryAgain();
                            }
                        }
                    });
            snackbar.show();
        } else {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Connection.checkConnection(v.getContext()))
                        data();

                }
            });
        }


    }

    private void tryAgain() {
        if (!Connection.checkConnection(this)) {
            Snackbar snackbar = Snackbar.make(layout, "No Internet Connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Try Again", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Connection.checkConnection(v.getContext())) {
                                data();
                            }
                        }
                    });
            snackbar.show();
        }
    }

    private void data() {

        disposable.add(cityService.listByCityName(edCity.getText().toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(),"There is no city.",Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }
                })
                .doOnNext(new Consumer<MainWeather>() {
                    @Override
                    public void accept(MainWeather mainWeather) throws Exception {
                        adapter.addList(mainWeather.getList());
                        adapter.notifyDataSetChanged();
                        list_city.setAdapter(adapter);
                    }
                }).subscribe()
                /*.subscribe(new Consumer<MainWeather>() {
                    @Override
                    public void accept(MainWeather mainWeather) throws Exception {


                    }
                })*/
        );
    }
}
