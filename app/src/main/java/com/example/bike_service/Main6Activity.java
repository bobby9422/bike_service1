package com.example.bike_service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main6Activity extends AppCompatActivity {
    private ArrayList<contactview> conlist = new ArrayList<>();
    Cursor resultSet;
    SQLiteDatabase mydatabase;
    Button all;
    Intent i;
    EditText search;
    ListView listView;
    SharedPreferences sharedpreferences;
    ArrayAdapter<contactview> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);this.setTitle("Customer ");
        sharedpreferences = getSharedPreferences("login", this.MODE_PRIVATE);
        adapter = new ContactViewAdapter(this, 0, conlist);
        all=(Button)findViewById(R.id.call);
        search=(EditText)findViewById(R.id.editText11);
        search.setVisibility(View.GONE);
        mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS user(vehicle VARCHAR,model VARCHAR,name VARCHAR,mobile VARCHAR,email VARCHAR,id VARCHAR);");
        resultSet = mydatabase.rawQuery("SELECT user.name, user.mobile, user.vehicle \n" +
                "FROM user where id='"+sharedpreferences.getString("id",null)+"'",null);
        try {
            if (resultSet.moveToFirst()) {
                do {
                    // Passing values
                    String name = resultSet.getString(0);
                    String mobile = resultSet.getString(1);
                    String vehicle = resultSet.getString(2);


                        conlist.add(new contactview(name, mobile, vehicle, false));
                } while (resultSet.moveToNext());
            }
        }
        catch(Exception e)
        {
            Toast.makeText(Main6Activity.this, "error"+e.getMessage(), Toast.LENGTH_LONG).show();

        }
//        vehlist.add(
//                new vehicleview("bobby","7020724885","MH14FE6320","28-06-2019"));



        listView = (ListView) findViewById(R.id.contactlist);
        listView.setAdapter(adapter);

      search.addTextChangedListener(new TextWatcher() {

          public void afterTextChanged(Editable s) {


          }

          public void beforeTextChanged(CharSequence s, int start,
                                        int count, int after) {
          }

          public void onTextChanged(CharSequence s, int start,
                                    int before, int count) {
              Toast.makeText(Main6Activity.this, "change"+s,
                      Toast.LENGTH_LONG).show();
              //adapter.getFilter().filter(conlist.contains(s));
          }
      });

        all.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    boolean flag = true;
                    if (adapter.getCount() > 0) {
                        for (int i = 0; i < adapter.getCount(); i++) {
                          //  Toast.makeText(Main6Activity.this, adapter.getCount(), Toast.LENGTH_LONG).show();
                            contactview selection = (contactview) adapter.getItem(i);
                            //Toast.makeText(Main6Activity.this, selection.getMobile() + selection.getChecked(),
                            //        Toast.LENGTH_LONG).show();
                        if( selection.getChecked())
                        {
                            SmsManager sms = SmsManager.getDefault();
                            sms.sendTextMessage("+91" +selection.getMobile() , null, "SHRI SAI AUTOMOBILE\nYOUR bile service date is here", null, null);
                            flag=false;
                        }
                        }
                    if(flag)
                    {
                        for (int i = 0; i < adapter.getCount(); i++)
                        {

                            contactview selection = (contactview) adapter.getItem(i);
                                SmsManager sms = SmsManager.getDefault();
                                sms.sendTextMessage("+91" +selection.getMobile() , null, new defMessage(Main6Activity.this).getMessage()+"\nYOUR bike service date is here", null, null);
                                flag=false;

                        }
                    }
                        Toast.makeText(Main6Activity.this, "Message Sent successfully!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Main6Activity.this, "No Customers",
                                Toast.LENGTH_LONG).show();

                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(Main6Activity.this, "Error:"+e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
