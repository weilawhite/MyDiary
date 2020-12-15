package com.example.mydiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    private boolean editable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        diary = (Diary) getIntent().getSerializableExtra("diary");
        editable=getIntent().getBooleanExtra("editable",false);
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

        if(!editable){
            titleEdit.setFocusable(false);
            titleEdit.setClickable(false);
            titleEdit.setLongClickable(false);
            bodyEdit.setFocusable(false);
            bodyEdit.setClickable(false);
            bodyEdit.setLongClickable(false);
            saveBtn.setVisibility(View.GONE);
        }


        saveBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle(R.string.message)
                        .setMessage(getResources().getString(R.string.save) + "\n" + diary.getTitle())
                        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                diary.setTitle(titleEdit.getText().toString());
                                diary.setBody(bodyEdit.getText().toString());
                                MainActivity.getMySQLite().update(diary);
                            }
                        });
                builder.create().show();

                break;
        }

    }
}