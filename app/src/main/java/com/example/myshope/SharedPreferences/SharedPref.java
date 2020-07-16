package com.example.myshope.SharedPreferences;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static SharedPreferences mSharedPref;
    public static final String PREF_NAME = "NAME";
    public static final String USER_EMAIL_ADDRESS = "userEmailAddress";
    public static final String USER_NAME = "username";
    public static final String IS_USER_LOGIN = "isUserLogin";

    private SharedPref() {
    }

    public static void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }
    public static void setUserEmailAddress(String email) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(USER_EMAIL_ADDRESS, email);
        editor.commit();
    }
    public static String getUserEmailAddress() {
        return mSharedPref.getString(USER_EMAIL_ADDRESS, null);
    }

    public static void setUserName(String userName){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(){
        return mSharedPref.getString(USER_NAME, null);
    }

    public static void setIsUserLogin(boolean isUserLogin){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean(IS_USER_LOGIN, isUserLogin);
        editor.commit();
    }

    public static Boolean getIsUserLogIn(){
        return mSharedPref.getBoolean(IS_USER_LOGIN, false);
    }
}