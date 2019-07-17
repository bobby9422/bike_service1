package com.example.bike_service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
EditText user,pass;
Button b1,b2;
View v;
TextView reg;
    String musername;
    String mpassword;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    SharedPreferences prf;
    Boolean doubleBackToExitPressedOnce=false;
    SQLiteDatabase mydatabase;
    Cursor resultSet;
    private static long back_pressed;
    TelephonyManager tel;
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
        tel = (TelephonyManager) this.getSystemService(MainActivity.TELEPHONY_SERVICE);

        this.getSupportActionBar().hide();
        sharedpreferences = getSharedPreferences("login", this.MODE_PRIVATE);
       // int k=this.MODE_PRIVATE;
        editor = sharedpreferences.edit();
        user=(EditText)findViewById(R.id.reg_name);
        pass=(EditText)findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button);
        reg=(TextView)findViewById(R.id.register_textView);
        reg.setVisibility(View.GONE);
       // Toast.makeText(MainActivity.this,"MainActivity.this onCreate invoked",Toast.LENGTH_LONG).show();
        mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS login(id VARCHAR,name VARCHAR,password VARCHAR(20),state VARCHAR);");
        if(sharedpreferences.contains("user") && sharedpreferences.contains("pass") && sharedpreferences.contains("id") && sharedpreferences.contains("state")){
//            resultSet = mydatabase.rawQuery("SELECT * FROM login", null);
//
//            if (resultSet.getCount() > 0) {
//                if (resultSet.moveToFirst()) {
//                    do {
////                        editor.putString("user", user.getText().toString().toLowerCase());
////                        editor.putString("pass", pass.getText().toString());
//                       // amobile.add("+91" + resultSet.getString(0) + ":" + resultSet.getString(1));
//
//                    } while (resultSet.moveToNext());
//                }
//            }


            if((sharedpreferences.getString("state","null").equals("1"))) {
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
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent   i = new Intent(MainActivity.this, register.class);
                // i.putExtra("user",user.getText().toString().toUpperCase());
                startActivity(i);
            }
        });
        b1.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {

try {
    musername=user.getText().toString().toUpperCase();
    mpassword=pass.getText().toString();

             resultSet = mydatabase.rawQuery("SELECT * FROM login", null);
             int active=0,check=0;
            String vu="",vp="",vi="",vs="";
            if (resultSet.getCount() > 0) {
                if (resultSet.moveToFirst()) {
                    do {
//                        editor.putString("user", user.getText().toString().toLowerCase());
//                        editor.putString("pass", pass.getText().toString());
                       // amobile.add("+91" + resultSet.getString(0) + ":" + resultSet.getString(1));
                      //  Toast.makeText(MainActivity.this,"u:"+resultSet.getString(1)+" P:"+resultSet.getString(2),Toast.LENGTH_LONG).show();

                        if(resultSet.getString(1).equals(musername)&&resultSet.getString(2).equals(mpassword))
                        {
                            if(resultSet.getString(3).equals("1"))
                            {
                                active=1;
                                vi=resultSet.getString(0);
                                vu=resultSet.getString(1).toUpperCase();
                                vp=resultSet.getString(2);
                                vs=resultSet.getString(3);
                            }
                            else
                            {
                               // Toast.makeText(MainActivity.this,"Account De-activated!!",Toast.LENGTH_LONG).show();
                                active=0;
                            }
                        check=1;
                        }
                        else{check=0;}

                    } while (resultSet.moveToNext());
                }
                if(check==1)
                {
                    if(active==1)
                    {
                        editor.putString("id",vi);
                        editor.putString("user",vu.toUpperCase());
                        editor.putString("pass",vp);
                        editor.putString("state",vs);
                        editor.commit();
                        Toast.makeText(MainActivity.this,"Successful Login!",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(i);
                    user.setText("");
                    pass.setText("");
                    finish();
                    }
                    else if(active==0)
                    {
                        Toast.makeText(MainActivity.this,"Account De-activated!!",Toast.LENGTH_LONG).show();
                        login("not_active");
                    }
                }
                else if(check==0)
                {
                    Toast.makeText(MainActivity.this,"Use regular username and password",Toast.LENGTH_LONG).show();

                }
            }
            else {
                login("active");
            }

//    Toast.makeText(MainActivity.this,"Unsuccessful Login!"+sb.toString(),Toast.LENGTH_LONG).show();
}
catch(Exception e)
{
    Toast.makeText(MainActivity.this,"djf"+e.getMessage()+e.getLocalizedMessage()+e.getCause(),Toast.LENGTH_LONG).show();
    e.getStackTrace();
}
//                if((user.getText().toString().equalsIgnoreCase("bobby")) && (pass.getText().toString().equals("login")))
//                {
//
//                    editor.putString("user", user.getText().toString().toLowerCase());
//                    editor.putString("pass", pass.getText().toString());
//                    editor.commit();
//                    Toast.makeText(MainActivity.this,"Successful Login!",Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
//                   // i.putExtra("user",user.getText().toString().toUpperCase());
//                    startActivity(i);
//                    user.setText("");
//                    pass.setText("");
//                    finish();
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this,"Unsuccessful Login!",Toast.LENGTH_LONG).show();
//                }

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
    @Override
    protected void onResume() {
        super.onResume();
        reg.setVisibility(View.GONE);
        new mainPermission().checkPerm(MainActivity.this);
        new mainPermission().checkPermCall(MainActivity.this);
    }
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

    public void login(String value)
    {
       final String value1=value;
        String getUrl="http://www.shrisaiautomobile.com/login/login.php?name="
                +musername+"&password="+mpassword;
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest getRequest = new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse (String response) {
                Log.v("TAG", "GET response: " + response);
                // Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
//String resp=response.toString();
                try {
                    if (!response.toString().equals("0")) {
                        JSONObject jsonObj = new JSONObject(response);


//                    JSONObject sys  = jsonObj.getJSONObject("id");
                        String id = jsonObj.getString("id");
                        String name = jsonObj.getString("name").toString().toUpperCase();
                        String password = jsonObj.getString("pass");
                        String state = jsonObj.getString("state");
                        String imei=jsonObj.getString("imei");
                      //  Toast.makeText(MainActivity.this,  tel.getDeviceId(), Toast.LENGTH_LONG).show();

                        if(imei.equals(tel.getDeviceId())) {
                            if (state.equals("1")) {
                                Toast.makeText(MainActivity.this, "Welcome " + name + "!!", Toast.LENGTH_LONG).show();

                                editor.putString("id", id);
                                editor.putString("user", name.toUpperCase());
                                editor.putString("pass", password);
                                editor.putString("state", state);
                                editor.commit();
                                /////////////////////////////////////////////////////////////////
                                if (value1.equals("active")) {
                                    mydatabase.execSQL("INSERT INTO login VALUES('" + id + "','" + name + "','" + password + "','" + state + "')");

                                } else if (value1.equals("not_active")) {
                                    mydatabase.execSQL("UPDATE login SET state = '" + state + "' WHERE id = '" + id + "'");
                                }
                                //////////////////////////////////////////////////////////////////
                                Toast.makeText(MainActivity.this, "Successful Login!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                                startActivity(i);
                                user.setText("");
                                pass.setText("");
                                finish();
                            } else if (state.equals("0")) {
                                Toast.makeText(MainActivity.this, "Disabled from Server!!", Toast.LENGTH_LONG).show();

                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "This Device not registered with this account!!!", Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Plz register!!!",Toast.LENGTH_LONG).show();
                        reg.setVisibility(View.VISIBLE);

                    }

                }
                catch (final JSONException e){
                    Toast.makeText(MainActivity.this,response+e.getMessage(),Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                if(error.getMessage()!=null){
                    Log.v("TAG", "Volley GET error: " + error.getMessage());
                    Toast.makeText(MainActivity.this,"Internet connection lost - Turn on switch ",Toast.LENGTH_LONG).show();
                }}
        });
        requestQueue.add(getRequest);
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
           // messageAll();
        }
        else if(requestCode==200 && grantResults[0]==-1)
        {
            //Toast.makeText(MainActivity.this,"Allow message permission!!" , Toast.LENGTH_LONG).show();
        }
        if(requestCode==300 && grantResults[0]==0)
        {
            // messageAll();
        }
        else if(requestCode==300 && grantResults[0]==-1)
        {

            //Toast.makeText(MainActivity.this,"Allow Call permission!!" , Toast.LENGTH_LONG).show();
        }
    }

}
