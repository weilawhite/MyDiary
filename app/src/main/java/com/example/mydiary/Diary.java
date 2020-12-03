package com.example.mydiary;

public class Diary {
    public static final String KEY_ID = "ID";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_BODY = "BODY";
    public static final String KEY_DATE = "DATE";

    public int id;
    private String title, body, date;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Diary(int id, String title, String body, String date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public Diary(String title, String body, String date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }
}
