package com.example.bike_service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ContactViewAdapter extends ArrayAdapter<contactview> {

    Cursor resultSet;
    SQLiteDatabase mydatabase;
    Intent i;
    private Context context;
    private List<contactview> conlist;
    contactview conviewlist;

    //constructor, call on creation
    public ContactViewAdapter(Context context, int resource, ArrayList<contactview> objects) {
        super(context, resource, objects);

        this.context = context;
        this.conlist = objects;
    }

    //called when rendering the list
    public View getView(final int position,final View convertView, final ViewGroup parent) {

        //get the property we are displaying
        conviewlist = conlist.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.contact_list, null);

        TextView name1 = (TextView) view.findViewById(R.id.name);
        final TextView mobile1 = (TextView) view.findViewById(R.id.mobile);
        final TextView vehicle1 = (TextView) view.findViewById(R.id.vehicle_no);
        final CheckBox check1=(CheckBox)view.findViewById(R.id.checkBox);

        //set address and description
        String name = conviewlist.getName() ;
        name1.setText(name);

        //set price and rental attributes
        mobile1.setText(String.valueOf(conviewlist.getMobile()));
        vehicle1.setText(String.valueOf(conviewlist.getVehicle()));
        check1.setChecked(conviewlist.getChecked());

        mobile1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

               // String[] parts = mobile1.getText().toString().split(":");
               // String mobile = parts[1]; // 034556
                // Toast.makeText(context, ""+mobile, Toast.LENGTH_LONG).show();
                try {
                    String mobile=conlist.get(position).getMobile().toString();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+91" + mobile));//change the number.
                    context.startActivity(callIntent);
                }
                catch(Exception e)
                {
                    Log.d("call",""+e.getMessage());
                }
            }

        });
        check1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String mobile=mobile1.getText().toString();
                // String[] parts = mobile1.getText().toString().split(":");
                // String mobile = parts[1]; // 034556
              //   Toast.makeText(context, ""+mobile+ check1.isChecked(), Toast.LENGTH_LONG).show();
               // conviewlist.setChecked(check1.isChecked()) ;
               // check1.setChecked(check1.isChecked());
                conlist.get(position).setChecked(check1.isChecked());
//                try {
//                    String mobile=mobile1.getText().toString();
//                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                    callIntent.setData(Uri.parse("tel:+91" + mobile));//change the number.
//                    context.startActivity(callIntent);
//                }
//                catch(Exception e)
//                {
//                    Log.d("call",""+e.getMessage());
//                }

          }

        });




        return view;
    }

}


