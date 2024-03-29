package com.example.bike_service;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    Intent i;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    SharedPreferences prf;
    SQLiteDatabase mydatabase;
    Cursor resultSet;
    private static long back_pressed;
    @Override
    public void onBackPressed()

    {

      if (back_pressed + 2000 > System.currentTimeMillis()) finish();
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.setTitle("Menu");
        mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS login(id VARCHAR,name VARCHAR,password VARCHAR(20),state VARCHAR);");

        prf = getSharedPreferences("login",MODE_PRIVATE);
       // Toast.makeText(Main2Activity.this,"Main2Activity.this onCreate invoked",Toast.LENGTH_LONG).show();

//        Intent alarmIntent = new Intent(this, BootCompletedIntentReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
//
//        manager = (AlarmManager)getSystemService(this.ALARM_SERVICE);
//        int interval = 1000;
//
//        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, interval, interval, pendingIntent);
//        Toast.makeText(this, "Alarm Set"+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();


//int flag=0;
//            //startService(new Intent(getBaseContext(), BikeService.class));
//        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
//        {
//            if ("com.example.bike_service.BikeService"
//                    .equals(service.service.getClassName()))
//            {
//            flag=1;
//        Toast.makeText(Main2Activity.this, "Service already running!",
//                Toast.LENGTH_LONG).show();
//        break;
//            }
//            else{
//                flag=0;
//
//            }
//        }
//        if(flag==0)
//        {
//            startService(new Intent(getBaseContext(), BikeService.class));
//        }
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this )
//              //  .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle("My notification")
//                .setContentText("Much longer text that cannot fit one line...")
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("Much longer text that cannot fit one line..."))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        NotificationManager mNotificationManager =
//
//                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(001, builder.build());


    }
    public void user(View v)
    {
        i = new Intent(Main2Activity.this, Main3Activity.class);
       // i.putExtra("user",user.getText().toString().toUpperCase());
        startActivity(i);
    }
    public void reminder(View v)
    {
        i = new Intent(Main2Activity.this, Main4Activity.class);
        // i.putExtra("user",user.getText().toString().toUpperCase());
        startActivity(i);
    }
    public void active_reminder(View v)
    {
        i = new Intent(Main2Activity.this, Main5Activity.class);
        // i.putExtra("user",user.getText().toString().toUpperCase());
        startActivity(i);
    }
    public void logout(View v)
    {
        SharedPreferences sharedpreferences = getSharedPreferences("login", this.MODE_PRIVATE);

        SharedPreferences.Editor editor = prf.edit();

        mydatabase.execSQL("UPDATE login SET state = '0' WHERE id = '"+sharedpreferences.getString("id","null")+"'");
        editor.clear();
        editor.commit();
        editor.apply();
        i = new Intent(Main2Activity.this, MainActivity.class);
        // i.putExtra("user",user.getText().toString().toUpperCase());

        startActivity(i);
        finish();
    }
    public void contact(View v)
    {
        i = new Intent(Main2Activity.this, Main6Activity.class);
        // i.putExtra("user",user.getText().toString().toUpperCase());
        startActivity(i);
    }

    public void service(View v)
    {
        i = new Intent(Main2Activity.this, ServiceAddActivity.class);
        // i.putExtra("user",user.getText().toString().toUpperCase());
        startActivity(i);
    }
    public void aservice(View v)
    {
        i = new Intent(Main2Activity.this, ServiceViewActivity.class);
        // i.putExtra("user",user.getText().toString().toUpperCase());
        startActivity(i);
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(Main2Activity.this,"Main2Activity.this onStart invoked",Toast.LENGTH_LONG).show();
//
//    }
    @Override
    protected void onResume() {
        super.onResume();
        new mainPermission().checkPerm(Main2Activity.this);
        new mainPermission().checkPermCall(Main2Activity.this);
        SharedPreferences sharedpreferences = getSharedPreferences("login", this.MODE_PRIVATE);
        if(sharedpreferences.contains("user") && sharedpreferences.contains("pass") && sharedpreferences.contains("id") && sharedpreferences.contains("state")) {
//        {
            // Toast.makeText(Main2Activity.this,"Main2Activity onResume invoked",Toast.LENGTH_LONG).show();
            JobScheduler jobScheduler = (JobScheduler) getApplicationContext()
                    .getSystemService(JOB_SCHEDULER_SERVICE);

            ComponentName componentName = new ComponentName(this,
                    logoutJob.class);
            boolean hasBeenScheduled = false;
            //Toast.makeText(Main2Activity.this, "job:" + jobScheduler.getAllPendingJobs(), Toast.LENGTH_LONG).show();
//
            for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
               // Toast.makeText(Main2Activity.this, "job:" + jobInfo, Toast.LENGTH_LONG).show();
//
                if (jobInfo != null) {
                    hasBeenScheduled = true;
                    break;
                } else {
                    hasBeenScheduled = false;
                }
            }
            if (hasBeenScheduled == false) {

                JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                        .setRequiredNetworkType(
                                JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true).setMinimumLatency(43200000).setOverrideDeadline(43200000).build();
                jobScheduler.schedule(jobInfo);
                //Toast.makeText(Main2Activity.this, "set", Toast.LENGTH_LONG).show();
//
            }
        }
        else
        {
            finish();
        }
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(Main2Activity.this,"Main2Activity onPause invoked",Toast.LENGTH_LONG).show();
//
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast.makeText(Main2Activity.this,"Main2Activity onStop invoked",Toast.LENGTH_LONG).show();
//
//    }
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(Main2Activity.this,"Main2Activity onRestart invoked",Toast.LENGTH_LONG).show();
//
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(Main2Activity.this,"Main2Activity onDestroy invoked",Toast.LENGTH_LONG).show();
//
//    }
@Override
public void onRequestPermissionsResult(
        int requestCode,
        String[] permissions,
        int[] grantResults
)
{
    if(requestCode==200 && grantResults[0]==0)
    {
        // messageAll();
    }
    else if(requestCode==200 && grantResults[0]==-1)
    {
        Toast.makeText(Main2Activity.this,"Allow message permission!!" , Toast.LENGTH_LONG).show();
    }
    if(requestCode==300 && grantResults[0]==0)
    {
        // messageAll();
    }
    else if(requestCode==300 && grantResults[0]==-1)
    {

        Toast.makeText(Main2Activity.this,"Allow Call permission!!" , Toast.LENGTH_LONG).show();
    }
}
public void setting(View v)
{
    AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setTitle("Custom Message");
    alert.setMessage("Add message that your customer will see!!");
// Create TextView
    final EditText input = new EditText (this);
    String msg=new defMessage(Main2Activity.this).getMessage();
    input.setText(""+msg);
    alert.setView(input);

    alert.setPositiveButton("SET", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            new defMessage(Main2Activity.this).setMessage( input.getText().toString());
            Toast.makeText(Main2Activity.this,"Settings applied",Toast.LENGTH_LONG).show();

        }
    });

    alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            // Canceled.
        }
    });
    alert.show();
}
}


