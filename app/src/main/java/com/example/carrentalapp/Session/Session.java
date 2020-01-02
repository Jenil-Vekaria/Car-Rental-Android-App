package com.example.carrentalapp.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    public static void close(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("session",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void save(Context context, String key, String value){

        SharedPreferences sharedPreferences = context.getSharedPreferences("session",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();

    }

    public static String read(Context context, String key, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences("session",Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,defaultValue);
    }


}
