package com.android.ict.weather.Util;

import com.android.ict.weather.Retrofit.APIClient;
import com.android.ict.weather.Retrofit.APIService;

public class Common {
    private static String BASE_URL="http://api.openweathermap.org/";
    public static APIService getAPI(){
        return APIClient.getClient(BASE_URL).create(APIService.class);
    }
}
