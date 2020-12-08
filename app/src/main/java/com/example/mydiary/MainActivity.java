package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button writeBtn, viewBtn, createBtn;
    TextView pageText;
    private static SQLiteDatabase database;
    private String dbName = "diary_book.db";
    private String tableName = "diary";

    public static MySQLite getMySQLite() {
        return mySQLite;
    }

    private static MySQLite mySQLite;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        Stetho.initializeWithDefaults(this);
        mySQLite = new MySQLite(this);/*
        handler = new Handler(Looper.myLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setTitle(Tools.getDateTime());
                handler.post(this);
            }
        });

*/
        if (mySQLite.openDatabase()) {
            Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show();
            pageText.setText(getResources().getString(R.string.page_text) + " " + mySQLite.getCount());
            return;
        }
        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
    }

    private void findView() {
        writeBtn = findViewById(R.id.write_btn);
        viewBtn = findViewById(R.id.view_btn);
        createBtn = findViewById(R.id.create_btn);
        pageText = findViewById(R.id.page_text);
        writeBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);
        createBtn.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        pageText.setText(getResources().getString(R.string.page_text) + " " + mySQLite.getCount());
        super.onRestart();
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.write_btn:
                Intent intent=new Intent(this,DiaryActivity.class);
                startActivity(intent);
                break;
            case R.id.view_btn:
                mySQLite.selectAll();
                break;
            case R.id.create_btn:
                mySQLite.createDatabase();
                pageText.setText(getResources().getString(R.string.page_text) + " " + mySQLite.getCount());
                break;
        }
    }

    public void close() {
        if (database != null) database.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            close();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}