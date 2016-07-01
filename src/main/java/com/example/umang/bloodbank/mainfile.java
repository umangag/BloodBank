package com.example.umang.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by UMANG on 3/2/2016.
 */
public class mainfile extends AppCompatActivity {

    Button bedonor, needdonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainfile);


        bedonor = (Button) findViewById(R.id.be_donor);
        needdonor = (Button) findViewById(R.id.need_donor);

        bedonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mainfile.this, donorpage.class);
                startActivity(i);

            }
        });

        needdonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(mainfile.this, needpage.class);

                startActivity(i);

            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
}
