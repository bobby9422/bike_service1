package com.example.bike_service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class mainPermission {

int a=200,b=300;
    public boolean checkPerm(Activity act)
    {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(act,new String[]{Manifest.permission.SEND_SMS},a);

//                Toast.LENGTH_LONG).show();
return false;
        }
        else
        {
            return true;
        }


    }
    public boolean checkPermCall(Activity act)
    {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(act,new String[]{Manifest.permission.SEND_SMS},b);

            //      Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return true;
        }


    }

}
