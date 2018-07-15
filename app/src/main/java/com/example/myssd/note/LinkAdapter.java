package com.example.myssd.note;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.myssd.note.modul.Link;
import java.util.ArrayList;


//Custom adapter
public class LinkAdapter extends ArrayAdapter<Link> {

    private Context mContext;
    private int id;
    private ArrayList<Link> items;
    Context ctx;
    LayoutInflater lInflater;

    public LinkAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Link> ar) {
        super(context, resource, ar);
        mContext = context;
        id = resource;
        items = ar;
        lInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    // position of element
    @Override
    public Link getItem(int position) { return items.get(position); }

    // id
    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View v, @NonNull ViewGroup parent) // методик, который меняет вид нашей вьюшечки
    {
        View mView = v ;
        if (mView == null) {
            mView = lInflater.inflate(R.layout.item, parent, false);
        }
        Link link = getLink(position);
        TextView textl = ((TextView) mView.findViewById(R.id.tvDescr));
        textl.setText(link.getJust_link());

        TextView textr = ((TextView) mView.findViewById(R.id.tvPrice));
        textr.setText(link.getDate());
        Log.d(link.getDate(),link.getDate());

        com.example.myssd.note.modul.Link s = getItem(position);

        textl.setTextColor(Color.BLACK);
        textr.setTextColor(Color.BLACK);

        if(s.getStatus()==1){ mView.setBackgroundColor(mContext.getResources().getColor(R.color.LightGreen)); }
        else if(s.getStatus()==2){ mView.setBackgroundColor(mContext.getResources().getColor(R.color.Crimson)); }
        else if(s.getStatus()==3){ mView.setBackgroundColor(mContext.getResources().getColor(R.color.LightGrey)); }
        else if(s.getStatus()==4){ mView.setBackgroundColor(mContext.getResources().getColor(R.color.LightCyan)); }
        else if(s.getStatus()==5){ mView.setBackgroundColor(mContext.getResources().getColor(R.color.LightYellow)); }
        return mView;
    }
    // link of position
    Link getLink(int position) { return ((Link) getItem(position)); }
}

