package com.example.mydiary;

import java.io.Serializable;

public class Diary implements Serializable {
    public static final String KEY_ID = "ID";
    public static final String KEY_TITLE = "TITLE";
    public static final String KEY_BODY = "BODY";
    public static final String KEY_DATE = "DATE";
    public static final String KEY_WEATHER = "WEATHER";

    public int id;
    private String title, body, date, weather;

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

    public String getWeather() {
        return weather;
    }

    public Diary(int id, String title, String body, String date, String weather) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.weather=weather;
    }

    public Diary(String title, String body, String date, String weather) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.weather = weather;
    }
}
