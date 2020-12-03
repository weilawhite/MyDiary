package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button writeBtn, viewBtn, createBtn;
    private static SQLiteDatabase database;
    private String dbName = "diary_book.db";
    private String tableName = "diary";
    private MySQLite mySQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        Stetho.initializeWithDefaults(this);
        mySQLite = new MySQLite(this);

        if (mySQLite.openDatabase()) {
            Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
    }

    private void findView() {
        writeBtn = findViewById(R.id.write_btn);
        viewBtn = findViewById(R.id.view_btn);
        createBtn = findViewById(R.id.create_btn);
        writeBtn.setOnClickListener(this);
        viewBtn.setOnClickListener(this);
        createBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.write_btn:
                mySQLite.insert(new Diary("標題?", "內容?", Tools.getDateTime()));
                break;
            case R.id.view_btn:
                mySQLite.delete(5);
                break;
            case R.id.create_btn:
                mySQLite.createDatabase();
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