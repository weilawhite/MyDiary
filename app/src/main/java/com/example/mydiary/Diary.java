package com.example.mydiary;

public class Diary {
    public static final String KEY_ID = "ID";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_BODY = "BODY";
    public static final String KEY_DATE = "DATE";

    public int id;
    private String title, body, date;

    public Diary(String title, String body, String date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }
}
