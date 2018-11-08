package com.android.ict.weather.Retrofit;

import com.android.ict.weather.model.ConditionList;
import com.android.ict.weather.model.Main;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface APIService {
    @Headers("x-api-key: 0e37b0f0091c490de207d168f595a78b")
    @GET("data/2.5/forecast?lat=16.871311&lon=96.199379")
    Observable<Main>getConditionList();

}
