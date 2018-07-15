package com.example.myssd.note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.myssd.note.modul.Link;
import java.util.ArrayList;
import java.util.List;

public class Second extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText mBodyText;
    Context x = this;
    AlertDialog.Builder alert;
    List<Link> links = new ArrayList<>();
    ArrayList<Link> local;
    Bundle extras;
    String name_of_this_note;

    void sendMessage(String msg) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        myIntent.putExtra(Intent.EXTRA_TEXT, msg);//
        startActivity(myIntent);
    }

    void Edit() {
        LinearLayout lila1 = new LinearLayout(x);
        final EditText input = new EditText(x);
        Spinner spinner = new Spinner(x);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        input.setText(name_of_this_note);
        lila1.setOrientation(LinearLayout.VERTICAL);
        lila1.addView(input);
        lila1.addView(spinner);
        alert = new AlertDialog.Builder(x);
        alert.setView(lila1);
        alert.setTitle("Edit note");
        alert.setPositiveButton("OK", (dialog, arg1) -> {
            int position = Integer.valueOf(extras.getString("position"));
            links = DatabaseInintializer.getLinks(AppDatabase.getAppDatabase(x));
            local = new ArrayList<>(links);
            int count = 0;
            for (Link loc : local) {
                if (position == count) {
                    if (!TextUtils.isEmpty(input.getText().toString())) {
                        loc.setJust_link(input.getText().toString());
                        loc.setStatus(spinner.getSelectedItemPosition() + 1);
                        loc.setText(mBodyText.getText().toString());
                        DatabaseInintializer.UpdateLink(AppDatabase.getAppDatabase(x), loc);
                        name_of_this_note = loc.getJust_link();
                        loc.setText(mBodyText.getText().toString());
                        start(loc.getJust_link(), loc.getText(), loc.getStatus());

                    }
                    break;
                }
                count++;
            }
        });
        alert.setNegativeButton("Cancel", (dialog, arg1) -> Toast.makeText(x, "Cancel", Toast.LENGTH_LONG).show());
        alert.show();
    }

    void Save() {
        int position = Integer.valueOf(extras.getString("position"));
        links = DatabaseInintializer.getLinks(AppDatabase.getAppDatabase(x));
        local = new ArrayList<>(links);
        int count = 0;
        for (Link loc : local) {
            if (position == count) {
                if (!TextUtils.isEmpty(mBodyText.getText().toString())) {
                    loc.setJust_link(getTitle().toString());
                    loc.setText(mBodyText.getText().toString());
                    DatabaseInintializer.UpdateLink(AppDatabase.getAppDatabase(x), loc);
                    name_of_this_note = loc.getJust_link();
                    start(loc.getJust_link(), loc.getText(), loc.getStatus());
                }
                break;
            }
            count++;
        }
    }

    void start(String name, String text, int color) {
        mBodyText.setText(text);
        name_of_this_note = name;
        setTitle(name);
        switch (color) {
            case 1:
                mBodyText.setBackgroundColor(getResources().getColor(R.color.LightGreen));
                break;
            case 2:
                mBodyText.setBackgroundColor(getResources().getColor(R.color.Crimson));
                break;
            case 3:
                mBodyText.setBackgroundColor(getResources().getColor(R.color.LightGrey));
                break;
            case 4:
                mBodyText.setBackgroundColor(getResources().getColor(R.color.LightCyan));
                break;
            case 5:
                mBodyText.setBackgroundColor(getResources().getColor(R.color.LightYellow));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Save();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        toolbar = findViewById(R.id.my_toolbar);
        extras = getIntent().getExtras();
        setSupportActionBar(toolbar);
        mBodyText = (EditText) findViewById(R.id.body);
        start(extras.getString("name"), extras.getString("text"), Integer.parseInt(extras.getString("color")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                sendMessage(mBodyText.getText().toString());
                return true;
            case R.id.edit:
                Edit();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressLint("AppCompatCustomView")
    public static class LinedEditText extends EditText {
        private Rect mRect;
        private Paint mPaint;

        // we need this constructor for LayoutInflater
        public LinedEditText(Context context, AttributeSet attrs) {
            super(context, attrs);

            mRect = new Rect();
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.BLACK);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int height = getHeight();
            int line_height = getLineHeight();

            int count = height / line_height;

            if (getLineCount() > count)
                count = getLineCount();

            Rect r = mRect;
            Paint paint = mPaint;
            int baseline = getLineBounds(0, r);

            for (int i = 0; i < count; i++) {
                canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
                baseline += getLineHeight();
                super.onDraw(canvas);
            }

        }
    }
}
