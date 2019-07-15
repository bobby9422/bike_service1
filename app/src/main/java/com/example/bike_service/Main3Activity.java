package com.example.bike_service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    Button add, can, up, search;
    EditText veh, mod, name, mob, email;
    Cursor resultSet;
    SQLiteDatabase mydatabase;
    String vehicle,model,name1,mobile,email1;
   Intent i;
    View v;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        this.setTitle("User");

        veh = (EditText) findViewById(R.id.editText3);
        mod = (EditText) findViewById(R.id.editText4);
        name = (EditText) findViewById(R.id.editText5);
        mob = (EditText) findViewById(R.id.editText6);
        email = (EditText) findViewById(R.id.editText7);
        add = (Button) findViewById(R.id.button6);
        can = (Button) findViewById(R.id.button8);
        up = (Button) findViewById(R.id.button10);
        search = (Button) findViewById(R.id.button11);
        mod.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        mob.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        up.setVisibility(View.GONE);
        search.setVisibility(View.VISIBLE);
        veh.setEnabled(true);
        sharedpreferences = getSharedPreferences("login", this.MODE_PRIVATE);
        mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS user(vehicle VARCHAR,model VARCHAR,name VARCHAR,mobile VARCHAR,email VARCHAR,id VARCHAR);");
        resultSet = mydatabase.rawQuery("Select * from user where id='"+sharedpreferences.getString("id","null")+"'", null);

        if (resultSet.getCount() == 0) {
           // Toast.makeText(Main3Activity.this, "" + resultSet.getCount(), Toast.LENGTH_LONG).show();
        }
        i=getIntent();
        String no=i.getStringExtra("vehicle");
      //  Toast.makeText(Main3Activity.this, "hello:" +no , Toast.LENGTH_LONG).show();
        veh.setText(no);
        if(!veh.getText().toString().isEmpty()){

            search(v);
        }
        // String username = resultSet.getString(0);

    }

    public void search(View v) {
        String vehicle;
        vehicle = veh.getText().toString();
        if (vehicle.isEmpty()) {
            Toast.makeText(Main3Activity.this, "Enter Vehicle No", Toast.LENGTH_LONG).show();
        } else if (!veh.getText().toString().isEmpty()) {
            resultSet = mydatabase.rawQuery("Select * from user where id='"+sharedpreferences.getString("id",null)+"' and vehicle='" + veh.getText().toString().toUpperCase() + "'", null);
            if (resultSet.getCount() == 0) {
                Toast.makeText(Main3Activity.this, "No User Found", Toast.LENGTH_LONG).show();
                veh.setEnabled(false);
                search.setVisibility(View.GONE);
                mod.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                mob.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                add.setVisibility(View.VISIBLE);

            } else if (resultSet.getCount() > 0) {
              //  Toast.makeText(Main3Activity.this, "from update", Toast.LENGTH_LONG).show();
                resultSet.moveToFirst();
                veh.setText(resultSet.getString(0));
                mod.setText(resultSet.getString(1));
                name.setText(resultSet.getString(2));
                mob.setText(resultSet.getString(3));
                email.setText(resultSet.getString(4));
                veh.setEnabled(false);
                search.setVisibility(View.GONE);
                mod.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                mob.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                up.setVisibility(View.VISIBLE);
            }
        }
    }

    public void add(View v) {
         vehicle= veh.getText().toString();
         model= mod.getText().toString();
         name1= name.getText().toString();
         mobile= mob.getText().toString();
         email1= email.getText().toString();
        if ((model.isEmpty()==true) || (name1.isEmpty()==true) || (mobile.isEmpty()==true) || (email1.isEmpty()==true)) {
            Toast.makeText(Main3Activity.this, "Enter all values", Toast.LENGTH_LONG).show();

        } else {
            try {
                mydatabase.execSQL("INSERT INTO user VALUES('" + veh.getText().toString().toUpperCase() + "','" + mod.getText().toString() + "','" + name.getText().toString() + "','" + mob.getText().toString() + "','" + email.getText().toString() + "','"+sharedpreferences.getString("id",null)+"');");
                Toast.makeText(Main3Activity.this, "Added Successfully", Toast.LENGTH_LONG).show();

                finish();
            } catch (Exception e) {
                Toast.makeText(Main3Activity.this, "error" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }

    public void update(View v) {
        vehicle= veh.getText().toString();
        model= mod.getText().toString();
        name1= name.getText().toString();
        mobile= mob.getText().toString();
        email1= email.getText().toString();
        if ((model.isEmpty()==true) || (name1.isEmpty()==true) || (mobile.isEmpty()==true) || (email1.isEmpty()==true)) {
            Toast.makeText(Main3Activity.this, "Enter all values", Toast.LENGTH_LONG).show();
        } else {
            try {
                mydatabase.execSQL("UPDATE user\n"+
                        "SET model = '"+model+"', mobile='"+mobile+"', email='"+email1+"', name= '"+name1+"'\n" +
                        "WHERE vehicle = '"+vehicle+"' and id='"+sharedpreferences.getString("id",null)+"'");
                Toast.makeText(Main3Activity.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(Main3Activity.this, "error" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void cancel(View v) {
        Toast.makeText(Main3Activity.this, "User not added or updated!!!", Toast.LENGTH_LONG).show();
        finish();
    }
}