package com.example.umang.bloodbank;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by UMANG on 3/2/2016.
 */
public class needpage extends AppCompatActivity {

    ImageButton aplus, abplus, aminus, bplus, bminus, oplus, abminus, ominus;
    String getbutton, firstnam;

    RequestQueue requestQueue;



    SessionManager session;

    ActionBarDrawerToggle mDrawerToggle;
    public Toolbar toolbar;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout Drawer;
    String TITLES[] = {"Home", "Be a Donor","Logout","Contact us"};
    int ICONS[] = {R.drawable.blood,R.drawable.blood,R.drawable.blood,R.drawable.blood};
    String NAME = "Abhishek";
    String EMAIL = "abhishek@eatloapp.com";
    String PHONE = "";
    int PROFILE;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.need);

        context = this;

        session=new SessionManager(getApplicationContext());

        aplus = (ImageButton) findViewById(R.id.imageButton);
        abplus = (ImageButton) findViewById(R.id.imageButton2);
        aminus = (ImageButton) findViewById(R.id.imageButton3);
        bplus = (ImageButton) findViewById(R.id.imageButton4);
        bminus = (ImageButton) findViewById(R.id.imageButton5);
        oplus = (ImageButton) findViewById(R.id.imageButton6);
        abminus = (ImageButton) findViewById(R.id.imageButton7);
        ominus = (ImageButton) findViewById(R.id.imageButton8);



        navDrawer();

        closeDrawerOnItemClick();

        aplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbutton("a+");
            }
        });

        abplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbutton("ab+");
            }
        });

        aminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbutton("a-");
            }
        });

        bplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbutton("b+");
            }
        });

        bminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbutton("b-");
            }
        });

        oplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbutton("O+");
            }
        });

        abminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbutton("ab-");
            }
        });

        ominus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedbutton("O-");
            }
        });
    }

    void selectedbutton(String selectbut) {
        getbutton = selectbut;
        apicall();
        System.out.println("hello" + firstnam);
    }

    void apicall() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        JSONArray jsonObject = new JSONArray();
        jsonObject.put( getbutton);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, "http://bloodbank.ml/php/retriveuser.php",
                jsonObject, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.print("helloik");
                try {
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.print("hellojj" + response);
                try {
                    System.out.print("helloj" + response);

                    ArrayList<DonorDetails> donorDetailsArrayList=new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jresponse = response.getJSONObject(i);

                        firstnam = jresponse.getString("First_name");
                        String getlastname = jresponse.getString("Last_name");
                        String getgender = jresponse.getString("Gender");
                        String getdob = jresponse.getString("Dob");
                        String getphone = jresponse.getString("Phone_number");
                        String getaddress = jresponse.getString("Address");
                        String gettime = jresponse.getString("Available_timings");
                        String getbloodgroup = jresponse.getString("Blood-group");

                        donorDetailsArrayList.add(new DonorDetails(firstnam,getlastname,getgender,getphone,getaddress,gettime,
                                getbloodgroup,getdob));

                        System.out.println(gettime);
                    }


                 Intent intent=new Intent(needpage.this,donordetailslist.class);
                    intent.putParcelableArrayListExtra("list",donorDetailsArrayList);
                    startActivity(intent);


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.print(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.print("helloerror" + error);
                try {
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);


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


        final GestureDetector mGestureDetector = new GestureDetector(needpage.this, new GestureDetector.OnGestureListener() {

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
                    Toast.makeText(needpage.this, "Clicked : " + recyclerView.getChildPosition(child), Toast.LENGTH_SHORT).show();
                    if (recyclerView.getChildPosition(child) == 3) {
                        session.logoutUser();
                        Intent i=new Intent(needpage.this,MainActivity.class);
                        startActivity(i);

                        Drawer.closeDrawers();
                    }
                    if (recyclerView.getChildPosition(child) == 2) {
                        Intent invite = new Intent(needpage.this, donorpage.class);
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
                        Intent invite = new Intent(needpage.this, mainfile.class);
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

    public void aplusclick(View view) {


    }

    public void abplusclick(View view) {

    }

    public void aminusclick(View view) {

    }

    public void bplusclick(View view) {

    }

    public void bminusclick(View view) {

    }

    public void oplusclick(View view) {

    }

    public void abminusclick(View view) {

    }

    public void ominusclick(View view) {

    }

}
