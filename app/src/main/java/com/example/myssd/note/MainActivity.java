package com.example.myssd.note;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.myssd.note.modul.Link;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String name;
    ListView listView;
    Context x = this;
    String text;
    List<Link> links = new ArrayList<>();
    ArrayList<Link> local;
    private Toolbar toolbar;
    LinkAdapter linkAd;
    AlertDialog.Builder alert;
    Map<Link, String> sort_date = new HashMap<Link, String>();
    Map<Link, String> sort_alpha = new HashMap<Link, String>();

    void addbutton() {
        LinearLayout lila1 = new LinearLayout(x);
        final EditText input = new EditText(x);
        Spinner spinner = new Spinner(x);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        input.setHint("Name");
        lila1.setOrientation(LinearLayout.VERTICAL);
        lila1.addView(input);
        lila1.addView(spinner);
        alert = new AlertDialog.Builder(x);
        alert.setView(lila1);
        alert.setTitle("Add note");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                if (input.getText().toString() != null) {
                    int selected =  spinner.getSelectedItemPosition();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String date_local = dateFormat.format(date);
                    DatabaseInintializer.populateSync(AppDatabase.getAppDatabase(x));
                    DatabaseInintializer.addLink(AppDatabase.getAppDatabase(x), new com.example.myssd.note.modul.Link(input.getText().toString(), selected+1, date_local));
                    links = DatabaseInintializer.getLinks(AppDatabase.getAppDatabase(x));
                    local = new ArrayList<>(links);
                    linkAd = new LinkAdapter(x, android.R.layout.simple_list_item_1, local);
                    listView.setAdapter(linkAd);
                }

            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(x, "Cancel", Toast.LENGTH_LONG).show();
            }
        });
        alert.show();


    }

    private static HashMap sortByDate(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    private static HashMap sortByAlpha(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(x, Second.class);
                int count = 0;
                for (Link loc : local) {
                    //
                    if(position==count)
                    {
                        Log.d(String.valueOf(position), loc.getJust_link());
                        i.putExtra( "name",loc.getJust_link());
                        i.putExtra("color",String.valueOf(loc.getStatus()));
                        break;
                    }
                    count++;
                }
                startActivity(i);
            }
        });
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        links = DatabaseInintializer.getLinks(AppDatabase.getAppDatabase(this));
        local = new ArrayList<>(links);
        linkAd = new LinkAdapter(this, android.R.layout.simple_list_item_1, local);
        listView.setAdapter(linkAd);

    }


    //smth changes
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_date:
                for (Link loc : links) {
                    sort_date.put(loc, loc.getDate());
                }
                Map<Link, String> map = sortByDate((HashMap) sort_date);
                local = new ArrayList<>(map.keySet());
                linkAd = new LinkAdapter(this, android.R.layout.simple_list_item_1, local);
                listView.setAdapter(linkAd);
                return true;
            case R.id.sort_alphabetically:
                for (Link loc : links) {
                    sort_alpha.put(loc, loc.getJust_link());
                }
                Map<Link, String> map1 = sortByAlpha((HashMap) sort_alpha);
                local = new ArrayList<>(map1.keySet());
                linkAd = new LinkAdapter(this, android.R.layout.simple_list_item_1, local);
                listView.setAdapter(linkAd);
                return true;
            case R.id.action_add:
                addbutton();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }


}
