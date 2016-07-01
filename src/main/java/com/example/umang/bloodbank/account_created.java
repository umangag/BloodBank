package com.example.umang.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by UMANG on 4/23/2016.
 */
public class account_created extends AppCompatActivity {

    TextView congrats, logincall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_created);

        congrats = (TextView) findViewById(R.id.congrats);
        logincall = (TextView) findViewById(R.id.logincall);

        logincall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(account_created.this, Signin.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, Signin.class);
        startActivity(i);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        MenuItem myMenuItem = menu.findItem(R.id.exit);
        MenuItem myMenuItem1 = menu.findItem(R.id.about_us);

        myMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                Intent i=new Intent(thank_you.this,MainActivity.class);
                finishAffinity();

                return false;
            }
        });

        myMenuItem1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent i = new Intent(account_created.this, aboutus.class);
                startActivity(i);
                return false;
            }
        });

        return true;
    }
}
