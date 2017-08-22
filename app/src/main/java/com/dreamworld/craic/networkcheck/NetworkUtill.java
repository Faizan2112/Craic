package com.dreamworld.craic.networkcheck;

/**
 * Created by faizan on 15/06/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dreamworld.craic.activity.DisplayDownloadActivity;

public class NetworkUtill {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context connContext) {
        int conn = NetworkUtill.getConnectivityStatus(connContext);
        String status = null;
        if (conn == NetworkUtill.TYPE_WIFI) {
            status = "Wifi enabled";
            // Toast.makeText(connContext.getApplicationContext(),""+status, );
        } else if (conn == NetworkUtill.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtill.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}