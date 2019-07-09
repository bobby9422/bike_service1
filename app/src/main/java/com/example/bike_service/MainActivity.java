package com.example.bike_service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText user,pass;
Button b1,b2;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    SharedPreferences prf;
    Boolean doubleBackToExitPressedOnce=false;
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "No exit!", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, MainActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Login");
        sharedpreferences = getSharedPreferences("login", this.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        user=(EditText)findViewById(R.id.editText);
        pass=(EditText)findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button);
        if(sharedpreferences.contains("user") && sharedpreferences.contains("pass")){
            if((sharedpreferences.getString("user","null").equalsIgnoreCase("bobby")) && (sharedpreferences.getString("pass","null").equals("login"))) {
//                Toast.makeText(MainActivity.this, "user:" + sharedpreferences.getString("user", "null")
//                        + "pass:" + sharedpreferences.getString("pass", "null"), Toast.LENGTH_LONG).show();
//                sharedpreferences.getString("pass", "null");
                Toast.makeText(MainActivity.this,"Successful Login!",Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                // i.putExtra("user",user.getText().toString().toUpperCase());
                startActivity(i);

            }
        }
        b1.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((user.getText().toString().equalsIgnoreCase("bobby")) && (pass.getText().toString().equals("login")))
                {

                    editor.putString("user", user.getText().toString().toLowerCase());
                    editor.putString("pass", pass.getText().toString());
                    editor.commit();
                    Toast.makeText(MainActivity.this,"Successful Login!",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                   // i.putExtra("user",user.getText().toString().toUpperCase());
                    startActivity(i);

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Unsuccessful Login!",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
    public void clear(View v)
    {
        user.setText("");
        pass.setText("");
        Toast.makeText(MainActivity.this,"Login again!",Toast.LENGTH_LONG).show();
    }
}
