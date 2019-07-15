package com.example.bike_service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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

    public class ListViewAdapter extends ArrayAdapter<vehicleview> implements  ActivityCompat.OnRequestPermissionsResultCallback{

        SharedPreferences sharedpreferences;
        SharedPreferences.Editor editor;
    SQLiteDatabase mydatabase;
        vehicleview vehviewlist;
        Intent i;
    private Context context;
    private List<vehicleview> vehlist;
        TextView name1 ;
        TextView mobile1=null;
        TextView vehicle1 =null;
        TextView date1;
        Button change;
        Button delete;
        Button srem;
        int pos;
    //constructor, call on creation
    public ListViewAdapter(Context context, int resource, ArrayList<vehicleview> objects) {
        super(context, resource, objects);

        this.context = context;
        this.vehlist = objects;
    }

    //called when rendering the list
    public View getView(final int position,final View convertView, final ViewGroup parent) {

        sharedpreferences = context.getSharedPreferences("login",Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        //get the property we are displaying
      vehviewlist = vehlist.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.list_item, null);

        name1 = (TextView) view.findViewById(R.id.name);
        mobile1 = (TextView) view.findViewById(R.id.mobile);
        vehicle1 = (TextView) view.findViewById(R.id.vehicle_no);
       date1 = (TextView) view.findViewById(R.id.date);
       change=(Button)view.findViewById(R.id.button17) ;
      delete=(Button)view.findViewById(R.id.button18) ;
      srem=(Button)view.findViewById(R.id.button14) ;

        //set address and description
        String name = String.valueOf(vehviewlist.getName()) ;
        name1.setText("Name:"+name);
             //set price and rental attributes
        mobile1.setText("Mobile:" + String.valueOf(vehviewlist.getMobile()));
       // Toast.makeText(context, String.valueOf(vehviewlist.getMobile())+":mobile:"+name+"pos:"+vehlist.get(position), Toast.LENGTH_LONG).show();

        vehicle1.setText("Vehicle No:" + String.valueOf(vehviewlist.getVehicle()));
        date1.setText("Service Date:" + String.valueOf(vehviewlist.getDate()));

        mobile1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {try {
               // Toast.makeText(context, "mobile:"+vehlist.get(position).getMobile().toString(), Toast.LENGTH_LONG).show();
String mobile=vehlist.get(position).getMobile().toString();
               // String[] parts = part.split(":");
             //   String mobile = parts[1]; // 034556
            //   Toast.makeText(context, ""+part, Toast.LENGTH_LONG).show();

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
try {
   // String[] parts = vehlist.get(position).getVehicle().toString().split(":");
    String vno = vehlist.get(position).getVehicle().toString(); // 034556
    //    Toast.makeText(context, ""+vno, Toast.LENGTH_LONG).show();
    i = new Intent(context, Main4Activity.class);
    i.putExtra("vehicle", vno.toString());
    context.startActivity(i);
}
catch (Exception e)
{
    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_LONG).show();
}
            }

        });
        srem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//            pos=position;
//                boolean a=new mainPermission().checkPerm(((Main5Activity)context));
//                if(a==true)
//                {
//                    messageAll(position);
//                }
//                else if(a==false)
//                {
//
//                }


                try {

                   // String[] parts = vehlist.get(position).getMobile().toString().split(":");
                    String mobile = vehlist.get(position).getMobile().toString();// 034556
                    //  Toast.makeText(context, "" + mobile, Toast.LENGTH_LONG).show();

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

                    //String[] parts = vehlist.get(position).getVehicle().toString().split(":");
                    String vno =  vehlist.get(position).getVehicle().toString(); // 034556
               //     Toast.makeText(context, ""+vno,
                 //           Toast.LENGTH_LONG).show();
                    mydatabase = context.openOrCreateDatabase("service", Context.MODE_PRIVATE, null);
                    mydatabase.execSQL("DELETE FROM rem WHERE vehicle='" + vno.toString().toUpperCase() + "' and id='"+sharedpreferences.getString("id","null")+"'");

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
        @Override
        public void onRequestPermissionsResult(
                int requestCode,
                String[] permissions,
                int[] grantResults
        )
        {
            if(requestCode==200 && grantResults[0]==0)
            {
                messageAll(pos);
            }
            else if(requestCode==200 && grantResults[0]==-1)
            {
                Toast.makeText(context,"Allow message permission!!" , Toast.LENGTH_LONG).show();
            }
        }

public void messageAll(int position)
{

}
    }


