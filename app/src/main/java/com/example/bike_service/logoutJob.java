package com.example.bike_service;

import android.app.Activity;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class logoutJob extends JobService
{
    SQLiteDatabase mydatabase;
    Context context;
        @Override
        public boolean onStartJob(JobParameters jobParameters) {
          //  context=Main2Activity.getApplicationContext();

            mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);

            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS login(id VARCHAR,name VARCHAR,password VARCHAR(20),state VARCHAR);");

            //Toast.makeText(logoutJob.this,"logout",Toast.LENGTH_LONG).show();
            SharedPreferences sharedpreferences = getSharedPreferences("login", this.MODE_PRIVATE);
            mydatabase.execSQL("UPDATE login SET state = '0' WHERE id = '"+sharedpreferences.getString("id","null")+"'");

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.clear();
            editor.commit();
            editor.apply();
//           Intent i = new Intent(logoutJob.this, MainActivity.class);
            // i.putExtra("user",user.getText().toString().toUpperCase());
mydatabase.close();
//            startActivity(i);


            return false;
        }

        @Override
        public boolean onStopJob(JobParameters jobParameters) {
            Toast.makeText(logoutJob.this,"logoutstop",Toast.LENGTH_LONG).show();

            return false;
        }
    }

