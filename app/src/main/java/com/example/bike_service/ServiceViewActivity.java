package com.example.bike_service;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ServiceViewActivity extends AppCompatActivity {
    private ArrayList<svaview> svalist = new ArrayList<>();
    Cursor resultSet;
    SQLiteDatabase mydatabase;
    Button all;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_view);
        this.setTitle("Active Service");
        ArrayAdapter<svaview> adapter = new svaviewadaptor(this, 0, svalist);
        all = (Button) findViewById(R.id.sva_all);
        mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS service(vehicle VARCHAR,sdate VARCHAR,edate VARCHAR,bill VARCHAR,summary VARCHAR);");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS user(vehicle VARCHAR,model VARCHAR,name VARCHAR,mobile VARCHAR,email VARCHAR);");
        Log.d("ppath", "path" + mydatabase.getPath());
        // Toast.makeText(Main5Activity.this, mydatabase.getPath(), Toast.LENGTH_LONG).show();


        resultSet = mydatabase.rawQuery("SELECT user.name, user.mobile, * \n" +
                "FROM service\n" +
                "INNER JOIN user ON service.vehicle = user.vehicle where service.edate=''", null);

        try {

            if (resultSet.moveToFirst()) {
                do {
                    String name = resultSet.getString(0);
                    String mobile = resultSet.getString(1);
                    String vehicle = resultSet.getString(2);
                    String sdate = resultSet.getString(3);


                    svalist.add(new svaview(sdate, name, mobile, vehicle));

                } while (resultSet.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(ServiceViewActivity.this, "error" + e.getMessage(), Toast.LENGTH_LONG).show();

        }
//        vehlist.add(
//                new vehicleview("bobby","7020724885","MH14FE6320","28-06-2019"));


        ListView listView = (ListView) findViewById(R.id.sva_list);
        listView.setAdapter(adapter);


//set the listener to the list view


    }
    public void all(View v)
    {
        try {


          //  mydatabase.execSQL("CREATE TABLE IF NOT EXISTS rem(vehicle VARCHAR,date VARCHAR,sn VARCHAR(20));");
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS user(vehicle VARCHAR,model VARCHAR,name VARCHAR,mobile VARCHAR,email VARCHAR);");
            resultSet = mydatabase.rawQuery("SELECT user.mobile \n" +
                    "FROM service\n" +
                    "INNER JOIN user ON service.vehicle = user.vehicle where service.edate=''", null);

            ArrayList<String> amobile = new ArrayList<String>();

            if (resultSet.getCount() > 0) {
                if (resultSet.moveToFirst()) {
                    do {

                        amobile.add("+91" + resultSet.getString(0));

                    } while (resultSet.moveToNext());
                }

                // Intent intent = new Intent(Main5Activity.this, Main5Activity.class);
                // PendingIntent pi = PendingIntent.getActivity(Main5Activity.this
                //        , 0, intent, 0);

                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager
                        sms = SmsManager.getDefault();
                for(String number : amobile) {


                        sms.sendTextMessage("" + number, null, "SHRI SAI AUTOMOBILE\nYOUR bike service is complete", null, null);
                     }
                Toast.makeText(ServiceViewActivity.this, "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(ServiceViewActivity.this, "No servicing today", Toast.LENGTH_LONG).show();

            }
        }
        catch(Exception e)
        {
            Toast.makeText(ServiceViewActivity.this, "error"+e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }



}