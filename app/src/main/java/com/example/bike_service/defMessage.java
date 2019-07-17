package com.example.bike_service;

import android.content.Context;
import android.content.SharedPreferences;

public class defMessage
{
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public defMessage(Context context)
    {
        sharedpreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }
    public void setMessage(String message)
    {

            editor.putString("message", message);
            editor.commit();


    }
    public String getMessage()
    {
        if(sharedpreferences.contains("message"))
        {
            return sharedpreferences.getString("message","null").toString();
        }
        else
        {
            return "From Bike Service";
        }

    }
}
