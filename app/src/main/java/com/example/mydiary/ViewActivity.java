package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    EditText titleEdit, bodyEdit;
    ImageView weatherImg;
    ImageButton saveBtn;
    Diary diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        diary = (Diary) getIntent().getSerializableExtra("diary");
        findView();

    }

    private void findView() {
        titleEdit = findViewById(R.id.title_edit);
        bodyEdit = findViewById(R.id.body_edit);
        weatherImg = findViewById(R.id.weather_img);
        saveBtn = findViewById(R.id.save_btn);

        int drawId = R.drawable.sun;
        if (diary.getWeather().equals("雨天")) {
            drawId = R.drawable.rain;
        } else if (diary.getWeather().equals("陰天")) {
            drawId = R.drawable.cloudy;
        }
        weatherImg.setImageResource(drawId);

        this.setTitle(diary.getDate());
        titleEdit.setText(diary.getTitle());
        bodyEdit.setText(diary.getBody());

        saveBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}