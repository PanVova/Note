package com.example.myssd.note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class Second extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText mBodyText;

    void sendMessage(String msg) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        myIntent.putExtra(Intent.EXTRA_TEXT, msg);//
        startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        toolbar = findViewById(R.id.my_toolbar);
        Bundle extras = getIntent().getExtras();
        // toolbar.setBackgroundColor(Color.parseColor("#80000000"));
        setSupportActionBar(toolbar);
        setTitle(extras.getString("name"));
        mBodyText = (EditText) findViewById(R.id.body);
        switch (Integer.parseInt(extras.getString("color"))) {
            case 1:
                mBodyText.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                mBodyText.setBackgroundColor(Color.RED);
                break;
            case 3:
                mBodyText.setBackgroundColor(Color.GRAY);
                break;
            case 4:
                mBodyText.setBackgroundColor(Color.CYAN);
                break;
            case 5:
                mBodyText.setBackgroundColor(Color.YELLOW);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                //добавить бд
                Toast.makeText(getApplicationContext(), "Save ", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.share:
                sendMessage(mBodyText.getText().toString());
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
            mPaint.setColor(Color.BLUE);
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
