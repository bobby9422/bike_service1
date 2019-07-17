package com.example.bike_service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class register extends AppCompatActivity {
EditText name,pass,mobile,shop,address;
Button reg,cancel;
String rname,rpass,rmobile,rshop,raddress;
String imei;
TelephonyManager tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.reg_name);
        pass=(EditText)findViewById(R.id.reg_pass);
        mobile=(EditText)findViewById(R.id.reg_mobile);
        shop=(EditText)findViewById(R.id.reg_shop);
        address=(EditText)findViewById(R.id.reg_add);
        reg=(Button) findViewById(R.id.reg_reg);
        cancel=(Button) findViewById(R.id.reg_cancel);
        tel = (TelephonyManager) this.getSystemService(register.TELEPHONY_SERVICE);

        imei=tel.getDeviceId();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rname=name.getText().toString().toUpperCase();
                rpass=pass.getText().toString();
                rmobile=mobile.getText().toString();
                rshop=shop.getText().toString();
                raddress=address.getText().toString();

                if(!rname.isEmpty()&&!rpass.isEmpty()&&!rmobile.isEmpty()&&!rshop.isEmpty()&&!raddress.isEmpty())
                {
                    register();
                }
                else
                {
                    Toast.makeText(register.this,"Enter all fields",Toast.LENGTH_LONG).show();

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(register.this,"Not registered!!",Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    public void register()
    {

        String getUrl="http://www.shrisaiautomobile.com/login/register.php" +
                "?name="+rname+"&&password="+rpass+"&&mobile="+rmobile+"&&shop="+rshop+"&&address="+raddress+"&&state=0&&imei="+imei;
        RequestQueue requestQueue = Volley.newRequestQueue(register.this);

        StringRequest getRequest = new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse (String response) {
                Log.v("TAG", "GET response: " + response);
                // Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
//String resp=response.toString();

                    if (response.toString().equals("New record created successfully"))
                    {
                        Toast.makeText(register.this,"Registered - Login again",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(register.this,"Error registering !!"+response,Toast.LENGTH_LONG).show();

                    }
                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                if(error.getMessage()!=null){
                    Log.v("TAG", "Volley GET error: " + error.getMessage());
                    Toast.makeText(register.this,"Internet connection lost - Turn on switch ",Toast.LENGTH_LONG).show();
                }}
        });
        requestQueue.add(getRequest);
    }

}
