package com.example.myssd.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {
    Context con;
    String[] colors;
    LayoutInflater inflater;
    public SpinnerAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
        con = context;
        colors = objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
       // inflater = con.getLayoutInflater();
        View row = inflater.inflate(R.layout.row, parent, false);
        TextView label = (TextView) row.findViewById(R.id.color);
        label.setText(colors[position]);
        switch (position) {
            case 0:
                label.setTextColor(con.getResources().getColor(R.color.LightGreen));
                break;
            case 1:
                label.setTextColor(con.getResources().getColor(R.color.Red));
                break;
            case 2:
                label.setTextColor(con.getResources().getColor(R.color.Gray));
                break;
            case 3:
                label.setTextColor(con.getResources().getColor(R.color.Cyan));
                break;
            case 4:
                label.setTextColor(con.getResources().getColor(R.color.yellow));
                break;
        }
        return row;
    }
}
