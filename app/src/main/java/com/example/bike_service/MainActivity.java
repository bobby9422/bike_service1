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
View v;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    SharedPreferences prf;
    Boolean doubleBackToExitPressedOnce=false;
    private static long back_pressed;
    @Override
    public void onBackPressed() {

      if (back_pressed + 2000 > System.currentTimeMillis()) finish();
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();

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
        Toast.makeText(MainActivity.this,"MainActivity.this onCreate invoked",Toast.LENGTH_LONG).show();

        if(sharedpreferences.contains("user") && sharedpreferences.contains("pass")){
            if((sharedpreferences.getString("user","null").equalsIgnoreCase("bobby")) && (sharedpreferences.getString("pass","null").equals("login"))) {
//                Toast.makeText(MainActivity.this, "user:" + sharedpreferences.getString("user", "null")
//                        + "pass:" + sharedpreferences.getString("pass", "null"), Toast.LENGTH_LONG).show();
//                sharedpreferences.getString("pass", "null");
                Toast.makeText(MainActivity.this,"Successful Login!",Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                // i.putExtra("user",user.getText().toString().toUpperCase());


                startActivity(i);
                finish();
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
                    user.setText("");
                    pass.setText("");
                    finish();
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
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(MainActivity.this,"MainActivity.this onStart invoked",Toast.LENGTH_LONG).show();
//
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(MainActivity.this,"MainActivity onResume invoked",Toast.LENGTH_LONG).show();
//
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(MainActivity.this,"MainActivity onPause invoked",Toast.LENGTH_LONG).show();
//
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast.makeText(MainActivity.this,"MainActivity onStop invoked",Toast.LENGTH_LONG).show();
//
//    }
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(MainActivity.this,"MainActivity onRestart invoked",Toast.LENGTH_LONG).show();
//
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(MainActivity.this,"MainActivity onDestroy invoked",Toast.LENGTH_LONG).show();
//
//    }
}
