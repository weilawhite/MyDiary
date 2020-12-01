package com.example.mydiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

    private void deleteDatabase() {
        context.deleteDatabase(dbName);
        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
    }

    private void createDatabase() {
        deleteDatabase();
        openDatabase();
    }

    public void closeDatabase() {
        if (database != null) {
            database.close();
            database=null;
        }
    }

    public boolean openDatabase() {
        String sqlstr = "create table if not exists " + tableName +
                "(" + Diary.KEY_ID + "INTEGER primary key autoincrement," +
                Diary.KEY_TITLE + "TEXT not null," +
                Diary.KEY_BODY + "TEXT not null," +
                Diary.KEY_DATE + "TEXT not null);";
        database = context.openOrCreateDatabase(dbName, context.MODE_PRIVATE, null);
        try {
            database.execSQL(sqlstr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
