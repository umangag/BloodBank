package com.example.umang.bloodbank;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by UMANG on 3/14/2016.
 */
public class thank_you extends AppCompatActivity {

    TextView statement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thank_you);


        statement = (TextView) findViewById(R.id.textView7);


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

                Intent i=new Intent(thank_you.this,aboutus.class);
                startActivity(i);
                return false;
            }
        });






        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(thank_you.this,mainfile.class);
        startActivity(i);

        super.onBackPressed();
    }
}