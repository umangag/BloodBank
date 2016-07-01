package com.example.umang.bloodbank;

/**
 * Created by UMANG on 4/15/2016.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

public class Receiver_checkData extends BroadcastReceiver {
    static boolean net = false;

    @Override
    public void onReceive(Context context, Intent intent) {
	/*	boolean noConnectivity = intent.getBooleanExtra(
				ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
		String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
		boolean isFailover = intent.getBooleanExtra(
				ConnectivityManager.EXTRA_IS_FAILOVER, false);

		NetworkInfo currentNetworkInfo = (NetworkInfo) intent
				.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
		NetworkInfo otherNetworkInfo = (NetworkInfo) intent
				.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

		if (currentNetworkInfo.isConnected()) {
			net = true;
			Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
		} else {
			net = false;
			Toast.makeText(context, "Not Connected", Toast.LENGTH_LONG).show();
		}*/


        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.d("NetworkCheckReceiver", "NetworkCheckReceiver invoked...");


            boolean noConnectivity = intent.getBooleanExtra(
                    ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

            if (!noConnectivity) {
                net=true;
//	            	Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
                Log.d("NetworkCheckReceiver", "connected");
            }
            else
            {
                net=false;
//	                Log.d("NetworkCheckReceiver", "disconnected");
//	            	Toast.makeText(context, "Not Connected", Toast.LENGTH_LONG).show();
            }
        }

    }

}
