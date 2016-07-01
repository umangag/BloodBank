package com.example.umang.bloodbank;

/**
 * Created by UMANG on 4/15/2016.
 */
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;


public class GetGoogleAdId {
    public AsyncResponse response = null;
    Context context;
    SessionManager session;
    String advertisingId = null;

    public void getId(Context context) {
        this.context = context;
        GetIdAsync gAsync = new GetIdAsync();
        gAsync.execute();
        session=new SessionManager(context);
    }

    public class GetIdAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            com.google.android.gms.ads.identifier.AdvertisingIdClient.Info adInfo = null;
            try {
                adInfo = com.google.android.gms.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo(context);
                if (adInfo != null) {
                    advertisingId = adInfo.getId();
                }
                System.out.println(advertisingId);
                boolean optOutEnabled = adInfo.isLimitAdTrackingEnabled();
                return advertisingId;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return advertisingId;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("GAID----" + s);
            session.setGoogleId(s);
        }
    }
}
