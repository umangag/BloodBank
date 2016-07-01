package com.example.umang.bloodbank;

/**
 * Created by UMANG on 3/1/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class signup extends AppCompatActivity {

    EditText fname, lname, email, password, phone, address;
    Spinner blood_group;
    Button signup_button;

    ArrayAdapter group_adapter;
    RadioGroup rg;
    RadioButton male,female;

    String group,sex;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        context=this;

        fname = (EditText) findViewById(R.id.first_name);
        lname = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone_number);
        address = (EditText) findViewById(R.id.address);

        blood_group = (Spinner) findViewById(R.id.spinner);

        rg= (RadioGroup) findViewById(R.id.radiogroup);
        male= (RadioButton) findViewById(R.id.radioMale);
        female= (RadioButton) findViewById(R.id.radioFemale);

        signup_button = (Button) findViewById(R.id.signupbutton);

        group_adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item);
        blood_group.setAdapter(group_adapter);
        group_adapter.add("A+");
        group_adapter.add("AB+");
        group_adapter.add("AB-");
        group_adapter.add("A-");
        group_adapter.add("B+");
        group_adapter.add("B-");
        group_adapter.add("O+");
        group_adapter.add("O-");

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callapi();

                Intent i=new Intent(signup.this, account_created.class);
                startActivity(i);
            }
        });
    }

    void callapi()
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
try
{
    sex = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();

    group = blood_group.getSelectedItem().toString();

}catch (Exception e)
{
    e.printStackTrace();
}



        String first = fname.getText().toString();
        String last = lname.getText().toString();
        String email_address = email.getText().toString();
        String user_pass = password.getText().toString();
        String contact = phone.getText().toString();
        String addre = address.getText().toString();
        String gender = sex;
        String user_blood_group = group;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("First_name", first);
            jsonObject.put("Last_name", last);
            jsonObject.put("Email_address", email_address);
            jsonObject.put("Password", user_pass);
            jsonObject.put("Gender", gender);
            jsonObject.put("Phone_number", contact);
            jsonObject.put("Blood_group", user_blood_group);
            jsonObject.put("Address", addre);



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://bloodbank.ml/php/usercreation.php",
                    jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!response.isNull("result")) {
                            String result = response.getString("result");
                            //loginresult(result);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}