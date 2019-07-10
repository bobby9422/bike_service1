package com.example.bike_service;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class svaviewadaptor extends ArrayAdapter<svaview> {
    Cursor resultSet;
    SQLiteDatabase mydatabase;
    Intent i;
    private Context context;
    private List<svaview> svalist;

    //constructor, call on creation
    public svaviewadaptor(Context context, int resource, ArrayList<svaview> objects) {
        super(context, resource, objects);

        this.context = context;
        this.svalist = objects;
    }

    //called when rendering the list
    public View getView(final int position, final View convertView, final ViewGroup parent) {

    //get the property we are displaying
    svaview svaviewlist = svalist.get(position);

    //get the inflater and inflate the XML layout for each item
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    final View view = inflater.inflate(R.layout.svalist_item, null);

    TextView name1 = (TextView) view.findViewById(R.id.sva_name);
    final TextView mobile1 = (TextView) view.findViewById(R.id.sva_mobile);
    final TextView vehicle1 = (TextView) view.findViewById(R.id.sva_vehicle_no);
    TextView date1 = (TextView) view.findViewById(R.id.sva_date);
    Button update = (Button) view.findViewById(R.id.sva_update);
    Button remove = (Button) view.findViewById(R.id.sva_remove);

        try {
    //set address and description
    String name = svaviewlist.getName();
    name1.setText(String.valueOf(name));

    //set price and rental attributes
    mobile1.setText(String.valueOf(svaviewlist.getMobile()));
    vehicle1.setText(String.valueOf(svaviewlist.getVehicle()));
    date1.setText(String.valueOf(svaviewlist.getDate()));

        mobile1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+91" + mobile1.getText().toString()));//change the number.
                    context.startActivity(callIntent);
                }
                catch(Exception e)
                {
                    Log.d("call",""+e.getMessage());
                }
            }

        });
        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                i = new Intent(context, ServiceAddActivity.class);
                i.putExtra("vehicle",vehicle1.getText().toString().toUpperCase());
                context.startActivity(i);
            }

        });

        remove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {


                    mydatabase = context.openOrCreateDatabase("service", Context.MODE_PRIVATE, null);
//                    Toast.makeText(context, "!"+vehicle1.getText().toString().toUpperCase()+"pp",
//                            Toast.LENGTH_LONG).show();
//                    Log.d("android","vehicle:"+vehicle1.getText().toString().toUpperCase());
                    mydatabase.execSQL("DELETE FROM service WHERE vehicle='" + vehicle1.getText().toString().toUpperCase() + "' and edate = ''");
                    Toast.makeText(context, "Deleted!",
                            Toast.LENGTH_LONG).show();
                    mydatabase.close();
                    ((ServiceViewActivity)context).finish();


                    Intent intent = new Intent(context, ServiceViewActivity.class);
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
    }
    catch(Exception e)
    {
        Log.d("android",""+e.getMessage());
    }
        return view;

    }

}


