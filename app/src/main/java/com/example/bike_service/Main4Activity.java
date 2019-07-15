package com.example.bike_service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main4Activity extends AppCompatActivity {
    Button search,adduser,set,cancel;
    TextView ak,tr,day;
    EditText veh,name,mob,nsn,akm,tkm,day1;
    RadioGroup rg;
    Cursor resultSet;
    SQLiteDatabase mydatabase;
    RadioButton r1,r2;
    Intent i;
    View v;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        this.setTitle("Reminder");
        mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);
        search=(Button) findViewById(R.id.button12) ;
        adduser=(Button) findViewById(R.id.button13) ;
        set=(Button) findViewById(R.id.button15) ;
        cancel=(Button) findViewById(R.id.button16) ;
        ak=(TextView) findViewById(R.id.textView3) ;
        tr=(TextView) findViewById(R.id.textView4) ;
        day=(TextView) findViewById(R.id.textView2) ;
        veh=(EditText)findViewById(R.id.editText8) ;
        name=(EditText)findViewById(R.id.editText9) ;
        mob=(EditText)findViewById(R.id.editText10) ;
        nsn=(EditText)findViewById(R.id.editText12) ;
        akm=(EditText)findViewById(R.id.editText16) ;
        tkm=(EditText)findViewById(R.id.editText17) ;
        day1=(EditText)findViewById(R.id.editText14) ;
        r1=(RadioButton) findViewById(R.id.radioButton) ;
        r2=(RadioButton) findViewById(R.id.radioButton2) ;
        rg=(RadioGroup)findViewById(R.id.rgroup);
        adduser.setVisibility(View.GONE);
        set.setVisibility(View.GONE);
        ak.setVisibility(View.GONE);
        tr.setVisibility(View.GONE);
        day.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        mob.setVisibility(View.GONE);
        nsn.setVisibility(View.GONE);
        akm.setVisibility(View.GONE);
        tkm.setVisibility(View.GONE);
        day1.setVisibility(View.GONE);
        rg.setVisibility(View.GONE);
        cancel.setVisibility(View.VISIBLE);
        sharedpreferences = getSharedPreferences("login", this.MODE_PRIVATE);
        Log.d("myTag", mydatabase.getPath());

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb=(RadioButton)findViewById(checkedId);
            //
                //
                //
                //    Toast.makeText(Main4Activity.this, ""+rb.getText(), Toast.LENGTH_LONG).show();
                if(rb.getText().equals("Total KM"))
                {
                    akm.setVisibility(View.VISIBLE);
                    tkm.setVisibility(View.VISIBLE);
                    day1.setVisibility(View.VISIBLE);
                    ak.setVisibility(View.VISIBLE);
                    tr.setVisibility(View.VISIBLE);
                    day.setVisibility(View.VISIBLE);
                    day1.setEnabled(false);
                    int a,b,c;
                    if((akm.getText().toString().isEmpty()==true)||(tkm.getText().toString().isEmpty()==true))
                    {
                        akm.setText("50");
                        tkm.setText("3000");
                        day1.setText("60");
                    }
                    else{
                        a=Integer.parseInt(akm.getText().toString());
                        b=Integer.parseInt(tkm.getText().toString());
                        if(a!=0){
                          //
                            // Toast.makeText(Main4Activity.this, "error", Toast.LENGTH_LONG).show();
                            c=b/a;
                            day1.setText(Integer.toString(c));
                        }
                        else if(a==0)
                        {
                            akm.setText("50");
                            tkm.setText("3000");
                            day1.setText("60");
                        }
                    }
                }
                else
                {
                    day1.setEnabled(true);
                    akm.setVisibility(View.GONE);
                    tkm.setVisibility(View.GONE);
                    day1.setVisibility(View.VISIBLE);

                    ak.setVisibility(View.GONE);
                    tr.setVisibility(View.GONE);
                    day.setVisibility(View.VISIBLE);
                    day1.setText("60");
                }
            }
        });
        akm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int a,b,c;
                if((akm.getText().toString().isEmpty()==true)||(tkm.getText().toString().isEmpty()==true))
                {
                    akm.setText("50");
                    tkm.setText("3000");
                    day1.setText("60");
                }
                else{
                    a=Integer.parseInt(akm.getText().toString());
                    b=Integer.parseInt(tkm.getText().toString());
                    if(a!=0){
                     //   Toast.makeText(Main4Activity.this, "error", Toast.LENGTH_LONG).show();
                        c=b/a;
                        day1.setText(Integer.toString(c));
                    }
                    else if(a==0)
                    {
                        akm.setText("50");
                        tkm.setText("3000");
                        day1.setText("60");
                    }
                }
            }
        });

        tkm.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                int a,b,c;
                if(tkm.getText().toString().isEmpty())
                {


                    tkm.setText("3000");
                    day1.setText("60");
                    akm.setText("50");
                }
                else {
                    a=Integer.parseInt(akm.getText().toString());
                    b=Integer.parseInt(tkm.getText().toString());
                    if(a!=0){
                     //   Toast.makeText(Main4Activity.this, "error", Toast.LENGTH_LONG).show();
                         c=b/a;
                          day1.setText(Integer.toString(c));
                    }
                    else if(a==0)
                    {
                        akm.setText("50");
                        tkm.setText("3000");
                        day1.setText("60");
                    }
                }

            }
        });
        day1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int a,b,c;
                String dayt=day1.getText().toString();
                //Toast.makeText(Main4Activity.this, ""+ day1.getText(), Toast.LENGTH_LONG).show();
                if((day1.getText().toString().isEmpty()==true))
                {
                    akm.setText("50");
                    tkm.setText("3000");
                    day1.setText("60");
                }
               else if(dayt.equals("0"))
                {
                 Toast.makeText(Main4Activity.this, "Enter days more then 0", Toast.LENGTH_LONG).show();
                    day1.setText("60");
                }

            }
        });

        try {
          //  mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS user(vehicle VARCHAR,model VARCHAR,name VARCHAR,mobile VARCHAR,email VARCHAR,id VARCHAR);");
        }
        catch(Exception e)
        {
            Toast.makeText(Main4Activity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        }
    public void search(View v)
    {
        String vehicle;
        vehicle = veh.getText().toString();
        if (vehicle.isEmpty()) {
            Toast.makeText(Main4Activity.this, "Enter Vehicle No", Toast.LENGTH_LONG).show();
        } else if (!vehicle.toString().isEmpty()) {
           // Toast.makeText(Main4Activity.this, "veh:"+vehicle+":", Toast.LENGTH_LONG).show();
            Log.d("vehicle","veh:"+vehicle+":");
            resultSet = mydatabase.rawQuery("Select * from user where id='"+sharedpreferences.getString("id",null)+"' andvehicle='" + vehicle.toString().toUpperCase() + "'", null);
            if (resultSet.getCount() == 0) {
              //  Toast.makeText(Main4Activity.this, "Create New User", Toast.LENGTH_LONG).show();
                i = new Intent(Main4Activity.this, Main3Activity.class);
                i.putExtra("vehicle",veh.getText().toString());
                startActivity(i);



            } else if (resultSet.getCount() > 0) {
             //   Toast.makeText(Main4Activity.this, "from update", Toast.LENGTH_LONG).show();
                adduser.setVisibility(View.GONE);
                set.setVisibility(View.VISIBLE);
                ak.setVisibility(View.VISIBLE);
                tr.setVisibility(View.VISIBLE);
                day.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                mob.setVisibility(View.VISIBLE);
                nsn.setVisibility(View.VISIBLE);
                akm.setVisibility(View.VISIBLE);
                tkm.setVisibility(View.VISIBLE);
                day1.setVisibility(View.VISIBLE);
                rg.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                day1.setEnabled(true);
                akm.setVisibility(View.GONE);
                tkm.setVisibility(View.GONE);
                day1.setVisibility(View.VISIBLE);
                name.setEnabled(false);
                mob.setEnabled(false);
                ak.setVisibility(View.GONE);
                tr.setVisibility(View.GONE);
                day.setVisibility(View.VISIBLE);
                resultSet.moveToFirst();
                veh.setText(resultSet.getString(0));
               // mod.setText(resultSet.getString(1));
                name.setText(resultSet.getString(2));
                mob.setText(resultSet.getString(3));
               // email.setText(resultSet.getString(4));
                veh.setEnabled(false);

            }
        }
    }
    public void cancel(View v)
    {
        Toast.makeText(Main4Activity.this, "reminder not set !!", Toast.LENGTH_LONG).show();
        finish();
    }
    public void add(View v)
    {
        i = new Intent(Main4Activity.this, Main3Activity.class);
        i.putExtra("vehicle",veh.getText().toString());
        startActivity(i);

    }
    public void set(View v)
    {

        String oldDate;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        oldDate = sdf.format(c.getTime());
        try{
            //Setting the date to the given date
            c.setTime(sdf.parse(oldDate));
        }catch(ParseException e){
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(day1.getText().toString()));
        //Date after adding the days to the given date
        String newDate = sdf.format(c.getTime());
        Log.d("Date",""+newDate);
        try {
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS rem(vehicle VARCHAR,date VARCHAR,sn VARCHAR(20),id VARCHAR);");
            mydatabase.execSQL("DELETE FROM rem WHERE id='"+sharedpreferences.getString("id",null)+"' and vehicle='" + veh.getText().toString().toUpperCase() + "'");
            mydatabase.execSQL("INSERT INTO rem VALUES('" + veh.getText().toString().toUpperCase() + "','" + newDate + "','" + nsn.getText().toString() + "','"+sharedpreferences.getString("id",null)+"')");
            Toast.makeText(Main4Activity.this, "Reminder Added Successfully", Toast.LENGTH_LONG).show();
            Log.d("Date","Reminder");
            finish();
        }
        catch(Exception e)
        {
            Toast.makeText(Main4Activity.this, "error:"+e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
//        String vehicle=veh.getText().toString();
//        if(!vehicle.isEmpty())
//        {
//
//            resultSet = mydatabase.rawQuery("Select * from user where vehicle='" + vehicle.toString().toUpperCase() + "'", null);
//            if (resultSet.getCount() != 0) {
//
//            }
//        }

        try {
        i=getIntent();
        String no=i.getStringExtra("vehicle");
            //Toast.makeText(Main4Activity.this, "nn", Toast.LENGTH_LONG).show();
        //    Toast.makeText(Main4Activity.this, "hello:" +no , Toast.LENGTH_LONG).show();

        if(!no.toString().isEmpty()){
            veh.setText(no);
            search(v);
        }}catch(Exception e)
    {
       // Toast.makeText(Main4Activity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
    }
    }

}
