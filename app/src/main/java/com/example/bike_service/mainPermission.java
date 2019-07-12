package com.example.bike_service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class mainPermission {
//    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
//    Activity my;
//    private int checkAndroidVersion(Activity context) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkPermission(context);
//return 1;
//        } else {
//            return 0;
//        }
//
//    }
//    private void checkPermission(Activity context) {
////         <uses-permission android:name="android.permission.CALL_PHONE" />
////    <uses-permission android:name="android.permission.SEND_SMS" />
//        if (ContextCompat.checkSelfPermission(context,
//                Manifest.permission.SEND_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale
//                    (context, Manifest.permission.READ_EXTERNAL_STORAGE) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale
//                            (context, Manifest.permission.CAMERA)) {
//
//                Snackbar.make(context.findViewById(android.R.id.content),
//                        "Please Grant Permissions to upload profile photo",
//                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ActivityCompat.requestPermissions(
//                                        new String[]{Manifest.permission
//                                                .READ_EXTERNAL_STORAGE);
//                            }
//                        }).show();
//            } else {
//                requestPermissions(
//                        new String[]{Manifest.permission
//                                .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
//                        PERMISSIONS_MULTIPLE_REQUEST);
//            }
//        } else {
//            // write your logic code if permission already granted
//        }
//    }
}
