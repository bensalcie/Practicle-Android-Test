package dev.bensalcie.practicletask.preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_FUSERNAME = "customername";
    private static final String KEY_EMAIL = "customeremail";
    private static final String KEY_PHONE = "cutomeraccount";

    private static final String KEY_ID = "customerid";

    private static SharedPrefManager mInstance;
    private static Context ctx;


    private SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_ID, user.getCustomerid());
        editor.putString(KEY_FUSERNAME, user.getCustomername());
        editor.putString(KEY_EMAIL, user.getCustomeremail());
        editor.putString(KEY_PHONE, user.getCutomeraccount());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getLong(KEY_ID, 1000),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_FUSERNAME, null),
                sharedPreferences.getString(KEY_PHONE, null)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}