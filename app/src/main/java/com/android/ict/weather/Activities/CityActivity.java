package com.android.ict.weather.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ict.weather.Adapter.WeatherAdapter;
import com.android.ict.weather.Adapter.WeatherAdapter1;
import com.android.ict.weather.BaseHolder.BaseViewHolder;
import com.android.ict.weather.Connection;
import com.android.ict.weather.Model.ConditionList;
import com.android.ict.weather.Model.MainWeather;
import com.android.ict.weather.R;
import com.android.ict.weather.Retrofit.APIService;
import com.android.ict.weather.Util.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;

public class CityActivity extends AppCompatActivity implements BaseViewHolder.onClick {
    @BindView(R.id.ed_city)
    EditText edCity;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.list_city)
    RecyclerView list_city;
    @BindView(R.id.tv_error)TextView tv_error;

    private APIService cityService;
    private CompositeDisposable disposable = new CompositeDisposable();
    private WeatherAdapter1 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        list_city.setLayoutManager(new LinearLayoutManager(this));
        list_city.setHasFixedSize(true);
        adapter = new WeatherAdapter1(this);
        adapter.setOnClickListener(this);

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MainWeather>(){

                    @Override
                    public void onNext(MainWeather mainWeather) {
                        tv_error.setVisibility(View.GONE);
                        list_city.setVisibility(View.VISIBLE);
                        adapter.addList(mainWeather.getList());
                        adapter.notifyDataSetChanged();
                       /* WeatherAdapter1 adapter1=new WeatherAdapter1(getApplicationContext(),mainWeather.getList());
                        adapter1.setOnClickListener(CityActivity.this);*/
                        list_city.setAdapter(adapter);

                    }

                    @Override
                    public void onError(Throwable e) {
                        tv_error.setVisibility(View.VISIBLE);
                        list_city.setVisibility(View.GONE);

                       // Toast.makeText(getApplicationContext(),"There is no city.",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {


                    }
                })

        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
            disposable.clear();

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
