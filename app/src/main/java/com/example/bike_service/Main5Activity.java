package com.example.bike_service;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main5Activity extends AppCompatActivity {
    private ArrayList<vehicleview> vehlist = new ArrayList<>();
    SharedPreferences sharedpreferences;
    Cursor resultSet;
    SQLiteDatabase mydatabase;
    Button all;
    Intent i;
    SimpleDateFormat sdf;
    Calendar cal1;
    Calendar cal2 ;
    String newDate ;
    Date d2 ;
    ArrayAdapter<vehicleview> adapter;
    Date d1;
    ArrayList<String> amobile;
    String oldDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        this.setTitle("Active Reminders");
        sharedpreferences = getSharedPreferences("login", this.MODE_PRIVATE);
        adapter = new ListViewAdapter(this, 0, vehlist);
        all=(Button)findViewById(R.id.all);
        mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS rem(vehicle VARCHAR,date VARCHAR,sn VARCHAR(20),id VARCHAR);");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS user(vehicle VARCHAR,model VARCHAR,name VARCHAR,mobile VARCHAR,email VARCHAR,id VARCHAR);");
        Log.d("ppath","path"+mydatabase.getPath());
       // Toast.makeText(Main5Activity.this, mydatabase.getPath(), Toast.LENGTH_LONG).show();


//        resultSet = mydatabase.rawQuery("SELECT user.name, user.mobile, user.vehicle, rem.date \n" +
//                "FROM user\n" +
//                "INNER JOIN rem ON user.vehicle = rem.vehicle ",null);
//        try {
//           sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar cal1 = Calendar.getInstance();
//            Calendar cal2 = Calendar.getInstance();
//            String newDate = sdf.format(cal2.getTime());
//            d2 = sdf.parse(newDate);
//
//            Date d1;
//            String oldDate;
//          //  Toast.makeText(Main5Activity.this, cal1+"/"+cal2, Toast.LENGTH_LONG).show();
//
//            if (resultSet.moveToFirst()) {
//                do {
//                    // Passing values
//                    String name = resultSet.getString(0);
//                    String mobile = resultSet.getString(1);
//                    String vehicle = resultSet.getString(2);
//                    String date = resultSet.getString(3);
//
//                    //d1 = sdf.parse(date);
//                    d1=new SimpleDateFormat("dd-MM-yyyy").parse(date);
//                    //Toast.makeText(Main5Activity.this, cal1+"/"+cal2, Toast.LENGTH_LONG).show();
//
//
//                   // Toast.makeText(Main5Activity.this, d2+"No one to send reminder"+d1, Toast.LENGTH_LONG).show();
//
//                    if(d1.compareTo(d2) <= 0 || d1.compareTo(d2)==0) {
//
//                        vehlist.add(
//                            new vehicleview(name, mobile, vehicle, date));}
//                } while (resultSet.moveToNext());
//            }
//        }
//        catch(Exception e)
//        {
//            Toast.makeText(Main5Activity.this, "error"+e.getMessage(), Toast.LENGTH_LONG).show();
//
//        }
//        vehlist.add(
//                new vehicleview("bobby","7020724885","MH14FE6320","28-06-2019"));






//set the listener to the list view

        all.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean a=new mainPermission().checkPerm(Main5Activity.this);
                if(a==true)
                {
                    messageAll();
                }
                else if(a==false)
                {

                }
            }

        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        vehlist.clear();
        resultSet = mydatabase.rawQuery("SELECT user.name, user.mobile, user.vehicle, rem.date \n" +
                "FROM user\n" +
                "INNER JOIN rem ON user.vehicle = rem.vehicle where user.id='"+sharedpreferences.getString("id",null)+"'",null);
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            String newDate = sdf.format(cal2.getTime());
            d2 = sdf.parse(newDate);

            Date d1;
            String oldDate;
            //  Toast.makeText(Main5Activity.this, cal1+"/"+cal2, Toast.LENGTH_LONG).show();

            if (resultSet.moveToFirst()) {
                do {
                    // Passing values
                    String name = resultSet.getString(0);
                    String mobile = resultSet.getString(1);
                    String vehicle = resultSet.getString(2);
                    String date = resultSet.getString(3);

                    //d1 = sdf.parse(date);
                    d1=new SimpleDateFormat("dd-MM-yyyy").parse(date);
                    //Toast.makeText(Main5Activity.this, cal1+"/"+cal2, Toast.LENGTH_LONG).show();


                    // Toast.makeText(Main5Activity.this, d2+"No one to send reminder"+d1, Toast.LENGTH_LONG).show();

                    if(d1.compareTo(d2) <= 0 || d1.compareTo(d2)==0) {

                        vehlist.add(
                                new vehicleview(name, mobile, vehicle, date));}
                } while (resultSet.moveToNext());
            }
        }
        catch(Exception e)
        {
            Toast.makeText(Main5Activity.this, "error"+e.getMessage(), Toast.LENGTH_LONG).show();

        }
//        vehlist.add(
//                new vehicleview("bobby","7020724885","MH14FE6320","28-06-2019"));


        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);


    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    )
    {
        if(requestCode==200 && grantResults[0]==0)
        {
            messageAll();
        }
        else if(requestCode==200 && grantResults[0]==-1)
        {
            Toast.makeText(Main5Activity.this,"Allow message permission!!" , Toast.LENGTH_LONG).show();
        }
        }

    public void messageAll()
    {
        try {

          //  Toast.makeText(Main5Activity.this, "From 5",
            //        Toast.LENGTH_LONG).show();
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS rem(vehicle VARCHAR,date VARCHAR,sn VARCHAR(20),id VARCHAR);");
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS user(vehicle VARCHAR,model VARCHAR,name VARCHAR,mobile VARCHAR,email VARCHAR,id VARCHAR);");
            resultSet = mydatabase.rawQuery("SELECT user.mobile,rem.date \n" +
                    "FROM user\n" +
                    "INNER JOIN rem ON user.vehicle = rem.vehicle where user.id='"+sharedpreferences.getString("id",null)+"' ", null);
            amobile = new ArrayList<String>();
            //String[] amobile={""};int i=0;
            if (resultSet.getCount() > 0) {
                if (resultSet.moveToFirst()) {
                    do {
                        // amobile[i]="+91" + resultSet.getString(0);
                        // amobile += "+91" + resultSet.getString(0) + ", ";
                        // Toast.makeText(Main5Activity.this, "" + resultSet.getString(0), Toast.LENGTH_LONG).show();
                        // i++;


                        amobile.add("+91" + resultSet.getString(0)+":"+resultSet.getString(1));

                    } while (resultSet.moveToNext());
                }

                // Intent intent = new Intent(Main5Activity.this, Main5Activity.class);
                // PendingIntent pi = PendingIntent.getActivity(Main5Activity.this
                //        , 0, intent, 0);

                //Get the SmsManager instance and call the sendTextMessage method to send message

                for(String number : amobile) {
                    String[] parts = number.toString().split(":");
                    String date1 = parts[1];
                    String no=parts[0];


                    // String dat = sdf.format("2019-06-25");
                    // Toast.makeText(Main5Activity.this, d2+"No one to send reminder"+dat, Toast.LENGTH_LONG).show();
                    d1=new SimpleDateFormat("dd-MM-yyyy").parse(date1);
                    //d1 = sdf.parse(date1);
                    //  Toast.makeText(Main5Activity.this, d2+"No one to send reminder"+d1, Toast.LENGTH_LONG).show();



                    if(d1.compareTo(d2) <= 0 || d1.compareTo(d2)==0) {

//                                if (ContextCompat.checkSelfPermission(Main5Activity.this,
//                                        Manifest.permission.SEND_SMS)
//                                        != PackageManager.PERMISSION_GRANTED) {
//                                    Toast.makeText(Main5Activity.this, "no Perm",
//                                            Toast.LENGTH_LONG).show();
//                                }
//                                else
//                                {
//                                    Toast.makeText(Main5Activity.this, "Perm",
//                                            Toast.LENGTH_LONG).show();
//                                }

                        SmsManager
                                sms = SmsManager.getDefault();
                        sms.sendTextMessage("" + no, null, "SHRI SAI AUTOMOBILE\nYOUR bile service date is here", null, null);
                    } }
                Toast.makeText(Main5Activity.this, "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
                //Toast.makeText(Main5Activity.this, "" + amobile, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(Main5Activity.this, "No one to send reminder", Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception e)
        {
            Toast.makeText(Main5Activity.this, "error"+e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }
}
