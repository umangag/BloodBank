package com.example.umang.bloodbank;

/**
 * Created by UMANG on 4/14/2016.
 */
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    SharedPreferences pref;
    Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Delivery";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_remember="Isremember";
    private static final String IS_rememberusername="remberUsername";
    private static final String IS_rememberPassword="remberpassword";

//    private static final String CAMERA_CHECK = "CameraCheck";
//    private static final String CURRENT_ORDERS_DONE="CurrentOrders";
    private static final String GAID="googleID";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }


    public void setGoogleId(String gaid){
        editor = pref.edit();
        editor.putString(GAID, gaid);
        editor.commit();
    }

  /*  public void currentOrdersToFetch(boolean fetch){
        editor = pref.edit();
        editor.putBoolean(CURRENT_ORDERS_DONE, fetch);
        editor.commit();
    }
    public void createCamera() {
        editor = pref.edit();
        editor.putBoolean(CAMERA_CHECK, true);
        editor.commit();
    }

    public void destroyCurrentOrders(){
        editor = pref.edit();
        editor.putBoolean(CURRENT_ORDERS_DONE, false);
        editor.commit();
    }
    public void destroyCamera() {
        editor = pref.edit();
        editor.putBoolean(CAMERA_CHECK, false);
        editor.commit();
    }

    public boolean isCurrentOrdersToFetch(){return pref.getBoolean(CURRENT_ORDERS_DONE,true);}

    public boolean isCameraDone() {
        return pref.getBoolean(CAMERA_CHECK, false);
    }
*/
    public void createLoginSession() {
        // Storing login value as TRUE
        editor = pref.edit();
        editor.putBoolean(IS_LOGIN, true);
//        editor.putBoolean(CAMERA_CHECK, false);

        // commit changes
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void set()
    {
        editor = pref.edit();
        editor.putBoolean(IS_remember, true);
//        editor.putBoolean(CAMERA_CHECK, false);

        // commit changes
        editor.commit();
    }

    public boolean Isremember() {
        return pref.getBoolean(IS_remember, false);
    }

    public void setIS_rememberPassword(String password)
    {
        editor = pref.edit();
        editor.putString(IS_rememberPassword, password);
//        editor.putBoolean(CAMERA_CHECK, false);

        // commit changes
        editor.commit();
    }

    public String getPasswordRember() {
        return pref.getString(IS_rememberPassword, null);
    }
    public void setIS_rememberUsername(String username)
    {
        editor = pref.edit();
        editor.putString(IS_rememberusername, username);
//        editor.putBoolean(CAMERA_CHECK, false);

        // commit changes
        editor.commit();
    }

    public String getUsernameRember() {
        return pref.getString(IS_rememberusername, null);
    }


    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, Signin.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name

        // return user
        return user;
    }

    public String getGAID(){
        return pref.getString(GAID,null);
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor = pref.edit();
        editor.clear();
        editor.commit();

    }

}

