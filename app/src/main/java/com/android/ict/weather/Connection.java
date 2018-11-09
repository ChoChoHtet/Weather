package com.android.ict.weather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connection {
    public static boolean checkConnection(Context context) {
        boolean isConnected = true;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected() && info.isAvailable()) {
            isConnected = true;
        } else {
            isConnected = false;
        }
        return isConnected;
    }
}
