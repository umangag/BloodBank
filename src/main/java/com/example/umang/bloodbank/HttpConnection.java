package com.example.umang.bloodbank;

/**
 * Created by UMANG on 4/5/2016.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class HttpConnection {

    private String menuJsonResult;
    public String JsonResult = null;
    public AsyncResponse response = null;
    Context context;
    ProgressDialog dialog;

    HttpConnection(Context context) {
        this.context = context;
    }

    public void accessWebService(String url_suffix, JsonObject jobj) {

        final String url_prefix = "http://eat-lo.com/backend/BLL/";
        String url = url_prefix + url_suffix;

//        jobj.addProperty(Constants.DELIVERY_BOY_ID, "5583ded962d44f7e208b458a");
//        jobj.addProperty(Constants.SESSION_KEY, "");
        String jsonObject = jobj.toString();

        System.out.println(jsonObject);

        try {
            JsonReadTask task = new JsonReadTask();
            // passes values for the urls string array
            task.execute(new String[] { url, jsonObject });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * public void sendData(String url,ArrayList<MenuDescription> cart) {
     * SendDataHttpAsyncTask task=new SendDataHttpAsyncTask(); task.execute(new
     * String []{url}); }
     */
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        URL url;
        HttpURLConnection urlConnection;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            // dialog = ProgressDialog.show(context,
            // "Getting data", "Please Wait while we fetch data");
            // dialog.setCancelable(true);
            // dialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            InputStream iStream = null;

            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            try {
                urlConnection = (HttpURLConnection) url.openConnection();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Connecting to url
            try {
                urlConnection.connect();
            } catch (IOException e) { // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Send Data

            // Reading data from url
            try {
                iStream = urlConnection.getInputStream();
            } catch (IOException e) { // TODO Auto-generated catch block
                e.printStackTrace();
            }
            menuJsonResult = inputStreamToString(iStream).toString();

            // System.out.println(menuJsonResult);

            return menuJsonResult;
            // return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            }

            catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error..." + e.toString(),
                        Toast.LENGTH_LONG).show();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            // sh();
            JsonResult = result;
            System.out.println(result);
            // response.processFinish(result);

            // dialog.cancel();
            // System.out.println(result);
        }
    }

	/*
	 * private class SendDataHttpAsyncTask extends AsyncTask<String, Void,
	 * String> {
	 *
	 * @Override protected String doInBackground(String... urls) {
	 *
	 *
	 * return POST(urls[0],cart); } // onPostExecute displays the results of the
	 * AsyncTask.
	 *
	 * @Override protected void onPostExecute(String result) {
	 *
	 * } }
	 */

}