package com.android.ict.weather.Retrofit;



import com.android.ict.weather.Model.MainWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService {
    @Headers("x-api-key: 0e37b0f0091c490de207d168f595a78b")
    @GET("data/2.5/forecast?lat=16.871311&lon=96.199379")
    Observable<MainWeather>getConditionList();

    @Headers("x-api-key: 0e37b0f0091c490de207d168f595a78b")
    @GET("data/2.5/forecast")
    Observable<MainWeather>listByCityName(@Query("q")String city_name);

}
