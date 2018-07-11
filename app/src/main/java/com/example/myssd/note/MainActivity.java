package com.example.myssd.note;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.myssd.note.modul.Link;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    final String[] catNames = new String[] {
            "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька"
    };
    ListView listView;
   // TextView tv;  // потом добавить
    Context x = this;
    List<Link> links = new ArrayList<>();
    ArrayList<Link> local;
    private Toolbar toolbar;
    LinkAdapter linkAd;
    void oclbtn(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String date_local = dateFormat.format(date);
        DatabaseInintializer.populateSync(AppDatabase.getAppDatabase(x));
        DatabaseInintializer.addLink(AppDatabase.getAppDatabase(x),new com.example.myssd.note.modul.Link("First",1,date_local));
        int count = DatabaseInintializer.getCount(AppDatabase.getAppDatabase(x));
       // Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.moduleb");
       //     intent.addCategory("com.example.myssd.note");
        //    intent.addCategory("com.example.myssd.note");
        //    Objects.requireNonNull(intent).putExtra("url","First");
        //    startActivity(intent);

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView= (ListView)findViewById(R.id.listview);
      //  ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catNames);
       // listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "Everything is working fine ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(x, Second.class);
                startActivity(i);
            }
        });
        toolbar = findViewById(R.id.my_toolbar);
       // toolbar.setBackgroundColor(Color.parseColor("#80000000"));
        setSupportActionBar(toolbar);
        links = DatabaseInintializer.getLinks(AppDatabase.getAppDatabase(this));
        local = new ArrayList<>(links);
        linkAd = new LinkAdapter(this, android.R.layout.simple_list_item_1, local );
        listView.setAdapter(linkAd);

    }





    //smth changes
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_date:
                Toast.makeText(getApplicationContext(), "Everything is working fine ", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sort_alphabetically:
                Toast.makeText(getApplicationContext(), "Everything is working fine ", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add:
                oclbtn();
                links = DatabaseInintializer.getLinks(AppDatabase.getAppDatabase(this));
                local = new ArrayList<>(links);
                linkAd = new LinkAdapter(this, android.R.layout.simple_list_item_1, local );
                listView.setAdapter(linkAd);
                Toast.makeText(getApplicationContext(), "Everything is working fine ", Toast.LENGTH_SHORT).show();
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
