package com.example.bike_service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class prviewadaptor extends ArrayAdapter<prview> {

    Cursor resultSet;
    SQLiteDatabase mydatabase;
    Intent i;
    private Context context;
    private List<prview> prlist;

    //constructor, call on creation
    public prviewadaptor(Context context, int resource, ArrayList<prview> objects) {
        super(context, resource, objects);

        this.context = context;
        this.prlist = objects;
    }

    //called when rendering the list
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        //get the property we are displaying
        prview prviewlist = prlist.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.prlist_item, null);

        TextView date1 = (TextView) view.findViewById(R.id.prlist_date);
        final TextView bill1 = (TextView) view.findViewById(R.id.prlist_bill);

        TextView amount1 = (TextView) view.findViewById(R.id.prlist_amount);


        //set address and description

        date1.setText(String.valueOf(prviewlist.getDate()));

        //set price and rental attributes
        bill1.setText(String.valueOf(prviewlist.getBill()));
        amount1.setText(String.valueOf(prviewlist.getAmount()));


        return view;
    }
}



