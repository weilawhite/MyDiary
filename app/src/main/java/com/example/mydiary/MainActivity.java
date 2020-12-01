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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();


        Stetho.initializeWithDefaults(this);
        create();
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

                insert();

                break;
            case R.id.view_btn:
                break;
            case R.id.create_btn:
                delete();
                create();
                break;


        }


    }

    private void create() {
        database = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        String sqlstr = "create table if not exists " + tableName +
                "(ID INTEGER primary key autoincrement," +
                "TITLE TEXT not null," +
                "BODY TEXT not null," +
                "DATE TEXT not null);";
        database.execSQL(sqlstr);
    }

    private void insert() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", "標題");
        contentValues.put("BODY", "內容");
        contentValues.put("DATE", getDateTime());
        database.insert(tableName, null, contentValues);
        Toast.makeText(this, R.string.write_success, Toast.LENGTH_SHORT).show();
    }

    private void delete() {
        deleteDatabase(dbName);
        Toast.makeText(this, R.string.delete_success, Toast.LENGTH_SHORT).show();

    }

    public String getDateTime() {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = new Date();
        String strDate = sdFormat.format(date);
//System.out.println(strDate);
        return strDate;
    }


    public void close() {
        if (database != null) database.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            close();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}