package com.example.umang.bloodbank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by UMANG on 5/15/2016.
 */
public class NorecordFound extends AppCompatActivity {

    TextView norecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.norecord);
        norecord = (TextView) findViewById(R.id.norecord);
    }
}
