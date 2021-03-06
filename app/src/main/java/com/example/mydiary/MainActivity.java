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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button writeBtn, viewBtn, createBtn;
    TextView pageText,username;
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




        mySQLite = new MySQLite(this);

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
        username=findViewById(R.id.username_text);
        writeBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);
        createBtn.setOnClickListener(this);

        Bundle bundle=getIntent().getExtras();
        username.setText(bundle.getString("username"));
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
                Intent intent = new Intent(this, DiaryActivity.class);
                startActivity(intent);
                break;
            case R.id.view_btn:
                Intent intent2 = new Intent(this, ListActivity.class);
                startActivity(intent2);
                //mySQLite.selectAll();
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