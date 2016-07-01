package com.example.umang.bloodbank;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by UMANG on 3/2/2016.
 */
public class donorpage extends AppCompatActivity {

    EditText firstname, lastname, dob, phonenumber, address;
    RadioButton male, female;
    Spinner avail_time, blood, am_pm;
    Button btn;

    ArrayAdapter blood_adapter, time_adapter, am_pm_adapter;
    String radiovalue, tym, bl_group, am_pm_value;
    RadioGroup radio_group;


    ActionBarDrawerToggle mDrawerToggle;
    public Toolbar toolbar;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    String TITLES[] = {"Home", "Need Blood","Contact-Us", "Logout"};
    int ICONS[] = {R.drawable.blood, R.drawable.blood, R.drawable.blood, R.drawable.blood};
    String NAME = "Abhishek";
    String EMAIL = "abhishek@eatloapp.com";
    String PHONE = "";
    //    int PROFILE = R.drawable.picker;
    int PROFILE;
    SessionManager session;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_page);
        navDrawer();
        context = this;

        session=new SessionManager(getApplicationContext());

        firstname = (EditText) findViewById(R.id.first_name);
        lastname = (EditText) findViewById(R.id.last_name);
        dob = (EditText) findViewById(R.id.dob);
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        address = (EditText) findViewById(R.id.address);

        radio_group = (RadioGroup) findViewById(R.id.radiogroup);
        male = (RadioButton) findViewById(R.id.radioMale);
        female = (RadioButton) findViewById(R.id.radioFemale);

        avail_time = (Spinner) findViewById(R.id.available_time);
        blood = (Spinner) findViewById(R.id.bloodgrp);
        am_pm = (Spinner) findViewById(R.id.am_pm);

        btn = (Button) findViewById(R.id.sub);

        blood_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        blood.setAdapter(blood_adapter);
        blood_adapter.add("A+");
        blood_adapter.add("AB+");
        blood_adapter.add("AB-");
        blood_adapter.add("A-");
        blood_adapter.add("B+");
        blood_adapter.add("B-");
        blood_adapter.add("O+");
        blood_adapter.add("O-");

        time_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        avail_time.setAdapter(time_adapter);
        time_adapter.add("1-2");
        time_adapter.add("3-4");
        time_adapter.add("5-6");
        time_adapter.add("6-7");
        time_adapter.add("7-8");
        time_adapter.add("8-9");
        time_adapter.add("10-11");
        time_adapter.add("12");

        am_pm_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        am_pm.setAdapter(am_pm_adapter);
        am_pm_adapter.add("AM");
        am_pm_adapter.add("PM");

        closeDrawerOnItemClick();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                apicall();

                Intent i = new Intent(donorpage.this, thank_you.class);
                startActivity(i);
            }
        });

    }

    void apicall() {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        try
        {
            radiovalue = ((RadioButton) findViewById(radio_group.getCheckedRadioButtonId())).getText().toString();
            tym = avail_time.getSelectedItem().toString();
            bl_group = blood.getSelectedItem().toString();
            am_pm_value = am_pm.getSelectedItem().toString();



        String first = firstname.getText().toString();
        String last = lastname.getText().toString();
        String date = dob.getText().toString();
        String phone = phonenumber.getText().toString();
        String add = address.getText().toString();
        String gender = radiovalue;
        String time = tym.concat(am_pm_value);
        String group = bl_group;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("First_name", first);
            jsonObject.put("Last_name", last);
            jsonObject.put("Gender", gender);
            jsonObject.put("Date_of_birth", date);
            jsonObject.put("Phone_number", phone);
            jsonObject.put("Address", add);
            jsonObject.put("Available_timings", time);
            jsonObject.put("Blood_group", group);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://bloodbank.ml/php/donor_insertion.php",
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

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    void navDrawer() {
        //  TextView tveatlo = (TextView) findViewById(R.id.eatlotooltext);
        // tveatlo.setTypeface(Fonts.getFont(context, Constants.AVENIR_REGULAR));

//        toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        mRecyclerView.setHasFixedSize(true);
        NAME = "";
        PHONE = "";

        mAdapter = new DrawerAdapter(TITLES, ICONS, NAME, PHONE, PROFILE, context);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);


        //mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener()
        mRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {


                Drawer.closeDrawers();

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
        });


        Drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {


                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }


        };
        Drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_cast_dark);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDrawerToggle.syncState();
    }

    void closeDrawerOnItemClick() {


        final GestureDetector mGestureDetector = new GestureDetector(donorpage.this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                System.out.println("umang Flinggggg");
                Drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

//                Drawer.closeDrawers();

                Drawer.setLongClickable(false);
                mRecyclerView.setLongClickable(false);

                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                try {
                    Thread.sleep(160);
                } catch (Exception e) {
                    System.out.println("hello umang");
                }


                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();
                    Toast.makeText(donorpage.this, "Clicked : " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                    if (recyclerView.getChildPosition(child) == 3) {
                        session.logoutUser();
                        Intent i=new Intent(donorpage.this,MainActivity.class);
                        startActivity(i);
                        Drawer.closeDrawers();
                    }
                    if (recyclerView.getChildPosition(child) == 2) {
                        Intent invite = new Intent(donorpage.this, needpage.class);
                        startActivity(invite);

                       /* invite.setAction(Intent.ACTION_SEND);
                        invite.setType("text/plain");
                        invite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        invite.putExtra(Intent.EXTRA_SUBJECT,
                                "Inviting you to download Eatlo");
                        invite.putExtra(Intent.EXTRA_TEXT,
                                "Download Eatlo to enjoy your daily meals");

                        startActivity(Intent.createChooser(invite,
                                "Invite Friends"));
*/
                    }

                    if (recyclerView.getChildPosition(child) == 1) {
                        Intent invite = new Intent(donorpage.this, mainfile.class);
                        startActivity(invite);


                    }
                    if (recyclerView.getChildPosition(child) == 4) {
                        Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.contact_us);
                        dialog.setCanceledOnTouchOutside(true);

                        TextView callCustomerCareDialog = (TextView) dialog.findViewById(R.id.callCustomerCareDialog);
                        TextView customerCareNumber = (TextView) dialog.findViewById(R.id.customercarenumber);
                        TextView emailDialog = (TextView) dialog.findViewById(R.id.emailDialog);
                        TextView emailId = (TextView) dialog.findViewById(R.id.emailid);
                        TextView whatsappDialog = (TextView) dialog.findViewById(R.id.whatsappDialog);
                        TextView whatsAppNumber = (TextView) dialog.findViewById(R.id.whatsappnumber);
                        callCustomerCareDialog.setTypeface(Fonts.getFont(context, Constants.AVENIR_REGULAR));
                        customerCareNumber.setTypeface(Fonts.getFont(context, Constants.AVENIR_REGULAR));
                        emailDialog.setTypeface(Fonts.getFont(context, Constants.AVENIR_REGULAR));
                        emailId.setTypeface(Fonts.getFont(context, Constants.AVENIR_REGULAR));
                        whatsappDialog.setTypeface(Fonts.getFont(context, Constants.AVENIR_REGULAR));
                        whatsAppNumber.setTypeface(Fonts.getFont(context, Constants.AVENIR_REGULAR));


                        callCustomerCareDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+918377841721"));
                                startActivity(intent);
//                                Toast.makeText(context, "Call Clicked", Toast.LENGTH_LONG).show();
                            }
                        });
                        emailDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", "care@bloodbank.ml", null));
//                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//                                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                                startActivity(Intent.createChooser(emailIntent, "Send email..."));
//                                Toast.makeText(context, "Email Clicked", Toast.LENGTH_LONG).show();
                            }
                        });
                        whatsappDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Uri uri = Uri.parse("smsto:" + "8377841721");
                                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                                    i.putExtra("sms_body", "Test");
                                    i.setPackage("com.whatsapp");
                                    startActivity(i);
                                }catch (Exception e)
                                {

                                }
//                                Toast.makeText(context, "Whatsapp Clicked", Toast.LENGTH_LONG).show();
                            }
                        });


                        dialog.show();
                    }


                    return true;

                }
                else if (child == null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                }
                return false;
            }



        });

    }



}
