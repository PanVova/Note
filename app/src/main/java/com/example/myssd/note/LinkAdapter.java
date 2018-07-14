package com.example.myssd.note;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myssd.note.modul.Link;

import java.util.ArrayList;


//Кастомный адаптер для возможности менять цветик в ячеечках
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

    // кол-во элементов

    @Override
    public int getCount() {
        return items.size();
    }

    // элемент по позиции
    @Override
    public Link getItem(int position) { return items.get(position); }

    // id по позиции
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
		

        com.example.myssd.note.modul.Link s = getItem(position);
//        TextView text = (TextView) mView.findViewById(R.id.tvDescr);

//        text.setText(s.getJust_link());
        textl.setTextColor(Color.BLACK);
        textr.setTextColor(Color.BLACK);

        //а вот тут собсн и происходит установка цвета бэкграунда по статусу ссылки (пока что стрингов)
        if(s.getStatus()==1){
            mView.setBackgroundColor(Color.GREEN);
        }
        else if(s.getStatus()==2){
            mView.setBackgroundColor(Color.RED);
        }
        else if(s.getStatus()==3){
            mView.setBackgroundColor(Color.GRAY);
        }
        else if(s.getStatus()==4){
            mView.setBackgroundColor(Color.CYAN);
        }
        else if(s.getStatus()==5){
            mView.setBackgroundColor(Color.YELLOW);
        }

        return mView;
    }

    // ссылка по позиции
    Link getLink(int position) {
        return ((Link) getItem(position));
    }


}

