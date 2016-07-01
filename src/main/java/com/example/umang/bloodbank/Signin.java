package com.example.umang.bloodbank;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by UMANG on 3/2/2016.
 */
public class Signin extends AppCompatActivity implements AsyncResponse {

    TextView text_view;
    EditText username, password;
    Button sign_in;
    CheckBox cb;

    Context context;
    SessionManager session;
    HttpConnection http_connection;
    Receiver_checkData net_check_receiver = new Receiver_checkData();

    static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    String result = "";
    String jsonResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        context = this;
        http_connection = new HttpConnection(context);
        http_connection.response = this;

        session = new SessionManager(getApplicationContext());

        IntentFilter filter = new IntentFilter(ACTION);
        this.registerReceiver(net_check_receiver, filter);

        boolean check = isNetConnected(context);
        if (check) {
            // Toast.makeText(c, "yes",Toast.LENGTH_LONG).show();
            Receiver_checkData.net = true;
        }

        if (getIntent().getBooleanExtra("EXIT", false) == true) {
            finish();
        }
        if (session.getGAID() == null) {
            GetGoogleAdId gId = new GetGoogleAdId();
            gId.response = (AsyncResponse) context;
            gId.getId(context);
        }


        text_view = (TextView) findViewById(R.id.textView4);
        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        sign_in = (Button) findViewById(R.id.sign_in);
        cb = (CheckBox) findViewById(R.id.checkBox);


        if(session.getUsernameRember()!=null){
            username.setText(session.getUsernameRember());
            password.setText(session.getPasswordRember());
        }

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Receiver_checkData.net) {
                    validate();
                    // Toast.makeText(getApplicationContext(),
                    // "Connected",Toast.LENGTH_LONG).show();

                } else {
                    Dialog netError = new Dialog(context);
                    netError.setTitle("No internet connectivity");
                    netError.show();
                    // Toast.makeText(getApplicationContext(),
                    // "Not Connected",Toast.LENGTH_LONG).show();
                }


                /*
				 * if(session.isLoggedIn())
				 * Toast.makeText(getApplicationContext(), "Yes",0).show(); else
				 * Toast.makeText(getApplicationContext(), "No",0).show();
				 */



            }
        });

    }

    public  void loginresult(String result){
        if (result.equals("success")) {

            if(cb.isChecked()){
                session.setIS_rememberUsername(username.getText().toString());
                session.setIS_rememberPassword(password.getText().toString());
            }

            Intent i = new Intent(Signin.this, mainfile.class);
            startActivity(i);

                /*    if (session.isLoggedIn()) {
                        Intent order = new Intent(Login.this,
                                OrdersList.class);
                        startActivity(order);

                        // Toast.makeText(getApplicationContext(),
                        // "Yes",0).show();
                    }\
                    else
                    {
                        session.createLoginSession();
                        Intent i = new Intent(Login.this,
                                StartTrip.class);
                        startActivity(i);
                    }
*/
        }
        if (result.equals("failed")) {
            Toast.makeText(getApplicationContext(),
                    "Information is  incorrect", Toast.LENGTH_LONG)
                    .show();
            username.setText("");
            password.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(net_check_receiver);
    }

    boolean isNetConnected(Context c2) {
        // TODO Auto-generated method stub

        ConnectivityManager connectivityMgr = (ConnectivityManager) c2
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // Check if wifi or mobile network is available or not. If any of them
        // is
        // available or connected then it will return true, otherwise false;
        if (wifi != null) {
            if (wifi.isConnected()) {
                return true;
            }
        }
        if (mobile != null) {
            if (mobile.isConnected()) {
                return true;
            }
        }
        return false;
    }

    void validate() {

        // Login API
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        String login = username.getText().toString();
        String input_password = password.getText().toString();

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("Email_address",login);
            jsonObject.put("Password",input_password);

            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, "http://bloodbank.ml/php/logindetails.php",
                    jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.cancel();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if(!response.isNull("result")) {
                            String result = response.getString("result");
                            loginresult(result);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.cancel();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // JSONObject for Login

        // Gson gson=new GsonBuilder().create();

    }


    @Override
    public void processFinish(String output, int responseCode) {
        // TODO Auto-generated method stub
        if (output != null) {
            // System.out.println("Output:  " + output);
            jsonResult = output;

        } else {
            System.out.println("NO INTERNET:  ");
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void getGoogleAdId(String output) {

    }


//    Intent i = new Intent(Signin.this, mainfile.class);
//                startActivity(i);


    @Override
    public void onBackPressed() {
     Intent i=new Intent(Signin.this,MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}




