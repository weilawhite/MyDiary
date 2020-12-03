package com.example.mydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class MySQLite {
    private String TAG = "MySQLite";
    private SQLiteDatabase database;
    private String dbName = "book.db";
    private String tableName = "diary";
    private Context context;

    public MySQLite(Context context) {
        this.context = context;
    }

    public void deleteDatabase() {
        context.deleteDatabase(dbName);
        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "刪除成功");
    }

    public void createDatabase() {
        deleteDatabase();
        openDatabase();
        Log.i(TAG, "重建成功");
    }

    public void closeDatabase() {
        if (database != null) {
            database.close();
            database = null;
        }
        Log.i(TAG, "關閉成功");
    }

    public boolean openDatabase() {
        String sqlstr = "create table if not exists " + tableName +
                "(" + Diary.KEY_ID + " INTEGER primary key autoincrement," +
                Diary.KEY_TITLE + " TEXT not null," +
                Diary.KEY_BODY + " TEXT not null," +
                Diary.KEY_DATE + " TEXT not null);";
        database = context.openOrCreateDatabase(dbName, context.MODE_PRIVATE, null);
        try {
            database.execSQL(sqlstr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

     void insert(Diary diary) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Diary.KEY_TITLE, diary.getTitle());
        contentValues.put(Diary.KEY_BODY,diary.getBody());
        contentValues.put(Diary.KEY_DATE,diary.getDate());
        database.insert(tableName, null, contentValues);
        Toast.makeText(context, R.string.write_success, Toast.LENGTH_SHORT).show();
    }

    public void delete(int id){
        database.delete(tableName,"id="+id,null);
    }

}
