package com.example.bike_service;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ServiceAddActivity extends AppCompatActivity {

    Button search,add,send,bill,delete;
    TextView name,mob,servicenote,date;
    EditText veh,brecord,bamount;
    Cursor resultSet;
    SQLiteDatabase mydatabase;
    Intent i;
    ArrayAdapter<prview> adapter;
    View v;
    private ArrayList<prview> prlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_add);
        this.setTitle("Service");

        try
        {

       adapter = new prviewadaptor(this, 0, prlist);
        mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);

        search=(Button) findViewById(R.id.sad_search_button) ;
        add=(Button) findViewById(R.id.sad_addService_button) ;
        send=(Button) findViewById(R.id.sad_send_button) ;
        bill=(Button) findViewById(R.id.sad_bill_button) ;
        delete=(Button) findViewById(R.id.sad_delete_button) ;

        name=(TextView) findViewById(R.id.sad_name_textView) ;
        mob=(TextView) findViewById(R.id.sad_mobile_textView) ;
        servicenote=(TextView) findViewById(R.id.sad_sn_textView) ;
        date=(TextView) findViewById(R.id.sad_date_textView) ;

        veh=(EditText)findViewById(R.id.sad_vehicleNo_editText) ;
        brecord=(EditText)findViewById(R.id.sad_bRecord_editText) ;
        bamount=(EditText)findViewById(R.id.sad_amount_editText) ;

        search.setVisibility(View.VISIBLE);
        add.setVisibility(View.GONE);
        send.setVisibility(View.GONE);
        bill.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);

        name.setVisibility(View.GONE);
        mob.setVisibility(View.GONE);
        servicenote.setVisibility(View.GONE);
        date.setVisibility(View.GONE);

        veh.setVisibility(View.VISIBLE);
        brecord.setVisibility(View.GONE);
        bamount.setVisibility(View.GONE);

        try {
            //  mydatabase = openOrCreateDatabase("service", MODE_PRIVATE, null);
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS user(vehicle VARCHAR,model VARCHAR,name VARCHAR,mobile VARCHAR,email VARCHAR);");
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS service(vehicle VARCHAR,sdate VARCHAR,edate VARCHAR,bill VARCHAR,summary VARCHAR);");
            mydatabase.execSQL("CREATE TABLE IF NOT EXISTS rem(vehicle VARCHAR,date VARCHAR,sn VARCHAR(20));");
            i=getIntent();
            String no=i.getStringExtra("vehicle");
            //  Toast.makeText(Main3Activity.this, "hello:" +no , Toast.LENGTH_LONG).show();
            veh.setText(no);
            if(!veh.getText().toString().isEmpty()){

                search(v);
            }
        }
        catch(Exception e)
        {
            Toast.makeText(ServiceAddActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }
}
catch(Exception e)
{
    Toast.makeText(ServiceAddActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();

}
    }
    public void search(View v){
            String vehicle;
            vehicle = veh.getText().toString();
            if (vehicle.isEmpty()) {
                Toast.makeText(ServiceAddActivity.this, "Enter Vehicle No", Toast.LENGTH_LONG).show();
            } else if (!vehicle.toString().isEmpty()) {

                resultSet = mydatabase.rawQuery("Select * from user where vehicle='" + veh.getText().toString().toUpperCase() + "'", null);
                if (resultSet.getCount() == 0) {
                    Toast.makeText(ServiceAddActivity.this, "Create New User", Toast.LENGTH_LONG).show();
                    i = new Intent(ServiceAddActivity.this, Main3Activity.class);
                    i.putExtra("vehicle", veh.getText().toString());
                    startActivity(i);


                } else if (resultSet.getCount() > 0) {

 Cursor prresultSet = mydatabase.rawQuery("SELECT * \n" +
                        "FROM service\n" +
                        "INNER JOIN user ON service.vehicle = user.vehicle where user.vehicle='"+vehicle.toUpperCase()+"' and service.edate!=''",null);
                try {

                    if (prresultSet.moveToFirst()) {
                        do {

                            String date = prresultSet.getString(2);
                            String amount = prresultSet.getString(3);

                            String bill = prresultSet.getString(4);


                                prlist.add(new prview(date,  bill, amount));
                        } while (prresultSet.moveToNext());
                    }
                    ListView listView = (ListView) findViewById(R.id.prlist);
                    listView.setAdapter(adapter);


                }
                catch(Exception e)
                {
                    Toast.makeText(ServiceAddActivity.this, "error"+e.getMessage(), Toast.LENGTH_LONG).show();

                }
                    Toast.makeText(ServiceAddActivity.this, "from update", Toast.LENGTH_LONG).show();
                    Cursor resultSet2 = mydatabase.rawQuery("Select * from service WHERE edate = '' and vehicle='" + veh.getText().toString().toUpperCase() + "'", null);

                    if (resultSet2.getCount() > 0) {
                        resultSet2.moveToFirst();
                        veh.setEnabled(false);
                        search.setVisibility(View.GONE);
                        name.setVisibility(View.VISIBLE);
                        mob.setVisibility(View.VISIBLE);
                        date.setVisibility(View.VISIBLE);
                        brecord.setVisibility(View.VISIBLE);
                        bill.setVisibility(View.VISIBLE);
                        send.setVisibility(View.VISIBLE);
                        bamount.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.VISIBLE);

                        resultSet.moveToFirst();
                        resultSet2.moveToFirst();
                        veh.setText(resultSet2.getString(0));
                        date.setText(resultSet2.getString(1));
                        bamount.setText(resultSet2.getString(3));
                        brecord.setText(resultSet2.getString(4));

                        name.setText(resultSet.getString(2));
                        mob.setText(resultSet.getString(3));

                    } else if (resultSet2.getCount() == 0) {
                        {
                            veh.setEnabled(false);
                            search.setVisibility(View.GONE);
                            name.setVisibility(View.VISIBLE);
                            mob.setVisibility(View.VISIBLE);
                            servicenote.setVisibility(View.VISIBLE);
                            add.setVisibility(View.VISIBLE);
                            resultSet.moveToFirst();
                            veh.setText(resultSet.getString(0));
                            // mod.setText(resultSet.getString(1));
                            name.setText(resultSet.getString(2));
                            mob.setText(resultSet.getString(3));
                            Cursor resultSet1 = mydatabase.rawQuery("Select sn from rem where vehicle='" + veh.getText().toString().toUpperCase() + "'", null);
                            if (resultSet1.getCount() == 0) {
                                servicenote.setText("No Note"); // mod.setText(resultSet.getString(1));
                            } else if (resultSet1.getCount() > 0) {
                                resultSet1.moveToFirst();
                                servicenote.setText(resultSet1.getString(0));
                            }
                        }
                    }
                }
            }

    }
    public void add(View v)
    {
        String oldDate;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        oldDate = sdf.format(c.getTime());
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS service(vehicle VARCHAR,sdate VARCHAR,edate VARCHAR,bill VARCHAR,summary VARCHAR);");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS rem(vehicle VARCHAR,date VARCHAR,sn VARCHAR(20));");

        mydatabase.execSQL("DELETE FROM rem WHERE vehicle='" + veh.getText().toString().toUpperCase() + "'");
        mydatabase.execSQL("INSERT INTO service VALUES('" + veh.getText().toString().toUpperCase() + "','" + oldDate + "','','','')");
        Toast.makeText(ServiceAddActivity.this, "Service Added Successfully", Toast.LENGTH_LONG).show();
        date.setText(oldDate);
        date.setVisibility(View.VISIBLE);
        brecord.setVisibility(View.VISIBLE);
        bill.setVisibility(View.VISIBLE);
        send.setVisibility(View.VISIBLE);
        bamount.setVisibility(View.VISIBLE);
        delete.setVisibility(View.VISIBLE);

    }
    public void send(View v)
    {
        try
        {
        String amount=bamount.getText().toString();
        String summary=brecord.getText().toString();
        if(!amount.equals("0"))
        {

                mydatabase.execSQL("UPDATE service\n"+
                        "SET bill = '"+amount+"', summary='"+summary+"'\n" +
                        "WHERE edate = '' and vehicle='"+veh.getText().toString().toUpperCase()+"'");
                SmsManager
                        sms = SmsManager.getDefault();
                sms.sendTextMessage("+91" + mob.getText().toString(), null, "SHRI SAI AUTOMOBILE\nYOUR BILL ESTIMATE IS"+amount, null, null);


                Toast.makeText(ServiceAddActivity.this, "Bill Amount Sent Successfully", Toast.LENGTH_LONG).show();


        }
        else
        {
            Toast.makeText(ServiceAddActivity.this, "Enter Some Amount to send message", Toast.LENGTH_LONG).show();
            if(!summary.equals(""))
            {
                mydatabase.execSQL("UPDATE service\n"+
                        "SET bill = '"+amount+"', summary='"+summary+"'\n" +
                        "WHERE edate = '' and vehicle='"+veh.getText().toString().toUpperCase()+"'");
            }
        }
        } catch (Exception e)
        {
        Toast.makeText(ServiceAddActivity.this, "error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
    public void bill(View v)
    {
        String oldDate;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        oldDate = sdf.format(c.getTime());

        try
        {
            String amount=bamount.getText().toString();
            String summary=brecord.getText().toString();
            if(!amount.equals("0"))
            {

                mydatabase.execSQL("UPDATE service\n"+
                        "SET bill = '"+amount+"', summary='"+summary+"', edate='"+oldDate+"'\n" +
                        "WHERE edate = '' and vehicle='"+veh.getText().toString().toUpperCase()+"'");
                SmsManager
                        sms = SmsManager.getDefault();
                sms.sendTextMessage("+91" + mob.getText().toString(), null, "SHRI SAI AUTOMOBILE\nYOUR BILL IS"+amount+"\n Service complete", null, null);


                Toast.makeText(ServiceAddActivity.this, "Service Complete", Toast.LENGTH_LONG).show();
                finish();

            }
            else
            {
                Toast.makeText(ServiceAddActivity.this, "Enter Some Amount to complete service", Toast.LENGTH_LONG).show();
                if(!summary.equals(""))
                {
                    mydatabase.execSQL("UPDATE service\n"+
                            "SET bill = '"+amount+"', summary='"+summary+"', edate='"+oldDate+"'\n" +
                            "WHERE edate = '' and vehicle='"+veh.getText().toString().toUpperCase()+"'");
                }
            }
        } catch (Exception e)
        {
            Toast.makeText(ServiceAddActivity.this, "error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void delete(View v)
    {
        try
        {
            mydatabase.execSQL("DELETE FROM service WHERE vehicle='" + veh.getText().toString().toUpperCase() + "' and edate = ''");
            Toast.makeText(ServiceAddActivity.this, "Service cancelled", Toast.LENGTH_LONG).show();
            finish();
        }
        catch (Exception e)
        {
            Toast.makeText(ServiceAddActivity.this, "error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
