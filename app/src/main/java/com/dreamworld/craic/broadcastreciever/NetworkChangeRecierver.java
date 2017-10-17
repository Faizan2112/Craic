package com.dreamworld.craic.broadcastreciever;

/**
 * Created by faizan on 15/06/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.dreamworld.craic.networkcheck.NetworkUtill;

public class NetworkChangeRecierver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtill.getConnectivityStatusString(context);

      // Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}
//<receiver
//            android:name="net.viralpatel.network.NetworkChangeReceiver"
//                    android:label="NetworkChangeReceiver" >
//<intent-filter>
//<action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
//<action android:name="android.net.wifi.WIFI_STATE_CHANGED" />
//</intent-filter>
//</receiver>