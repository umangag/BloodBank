package com.example.umang.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by UMANG on 5/15/2016.
 */
public class aboutus extends AppCompatActivity {

    ImageView image;
    TextView details, blood, contact, developed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);

        details = (TextView) findViewById(R.id.detail);
        blood = (TextView) findViewById(R.id.bloodbank);
        contact = (TextView) findViewById(R.id.contact);
        developed = (TextView) findViewById(R.id.developed);

    }

}
