package com.example.bike_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootCompletedIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
            Intent pushIntent = new Intent(context, BikeService.class);
            context.startService(pushIntent);
        }
        //Toast.makeText(context, "I'm running"+intent.getAction(), Toast.LENGTH_SHORT).show();

    }
}
