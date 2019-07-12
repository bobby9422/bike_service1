package com.example.bike_service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

    public class ListViewAdapter extends ArrayAdapter<vehicleview> {

    Cursor resultSet;
    SQLiteDatabase mydatabase;
    Intent i;
    private Context context;
    private List<vehicleview> vehlist;

    //constructor, call on creation
    public ListViewAdapter(Context context, int resource, ArrayList<vehicleview> objects) {
        super(context, resource, objects);

        this.context = context;
        this.vehlist = objects;
    }

    //called when rendering the list
    public View getView(final int position,final View convertView, final ViewGroup parent) {

        //get the property we are displaying
        vehicleview vehviewlist = vehlist.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.list_item, null);

        TextView name1 = (TextView) view.findViewById(R.id.name);
        final TextView mobile1 = (TextView) view.findViewById(R.id.mobile);
         final TextView vehicle1 = (TextView) view.findViewById(R.id.vehicle_no);
        TextView date1 = (TextView) view.findViewById(R.id.date);
        Button change=(Button)view.findViewById(R.id.button17) ;
        Button delete=(Button)view.findViewById(R.id.button18) ;
        Button srem=(Button)view.findViewById(R.id.button14) ;

        //set address and description
        String name = vehviewlist.getName() ;
        name1.setText("Name:"+name);

              //set price and rental attributes
        mobile1.setText("Mobile:" + String.valueOf(vehviewlist.getMobile()));
        vehicle1.setText("Vehicle No:" + String.valueOf(vehviewlist.getVehicle()));
        date1.setText("Service Date:" + String.valueOf(vehviewlist.getDate()));

        mobile1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String[] parts = mobile1.getText().toString().split(":");
                String mobile = parts[1]; // 034556
               // Toast.makeText(context, ""+mobile, Toast.LENGTH_LONG).show();
                try {
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
        change.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String[] parts = vehicle1.getText().toString().split(":");
                String vno = parts[1]; // 034556
            //    Toast.makeText(context, ""+vno, Toast.LENGTH_LONG).show();
                i = new Intent(context, Main4Activity.class);
                i.putExtra("vehicle",vno.toString());
                context.startActivity(i);
            }

        });
        srem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {

                    String[] parts = mobile1.getText().toString().split(":");
                    String mobile = parts[1]; // 034556
                  //  Toast.makeText(context, "" + mobile, Toast.LENGTH_LONG).show();
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "no Perm",
                                Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(context, "Perm",
                                Toast.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(context, Main5Activity.class);

                    PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

                    //Get the SmsManager instance and call the sendTextMessage method to send message
                    SmsManager
                            sms = SmsManager.getDefault();
                    sms.sendTextMessage("+91" + mobile, null, "SHRI SAI AUTOMOBILE\nYOUR bile service date is here", pi, null);
                    ((Main5Activity)context).finish();
                    Toast.makeText(context, "Message Sent successfully!",
                            Toast.LENGTH_LONG).show();

                }
                catch(Exception e)
                {
                    Log.d("android",""+e.getMessage());
                    Toast.makeText(context, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }

        });
        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {

                    String[] parts = vehicle1.getText().toString().split(":");
                    String vno = parts[1]; // 034556
               //     Toast.makeText(context, ""+vno,
                 //           Toast.LENGTH_LONG).show();
                    mydatabase = context.openOrCreateDatabase("service", Context.MODE_PRIVATE, null);
                    mydatabase.execSQL("DELETE FROM rem WHERE vehicle='" + vno.toString().toUpperCase() + "'");
                    Toast.makeText(context, "Deleted!",
                            Toast.LENGTH_LONG).show();
                    ((Main5Activity)context).finish();


                    Intent intent = new Intent(context, Main5Activity.class);
                    context.startActivity(intent);



                }
                catch(Exception e)
                {
                    Log.d("android",""+e.getMessage());
                    Toast.makeText(context, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }

        });
        return view;
    }
}


