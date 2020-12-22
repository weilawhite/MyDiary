package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {

    EditText titleEdit, bodyEdit;
    Spinner weatherSpn;
    Button okBtn, cancelBtn;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        date = Tools.getDateTime();
        findView();


    }


    private void findView() {
        titleEdit = findViewById(R.id.title_edit);
        bodyEdit = findViewById(R.id.body_edit);
        weatherSpn = findViewById(R.id.weather_spn);
        okBtn = findViewById(R.id.ok_btn);
        cancelBtn = findViewById(R.id.cancel_btn);
        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ok_btn:
                String title = titleEdit.getText().toString();
                String body = bodyEdit.getText().toString();
                String weather = weatherSpn.getSelectedItem().toString();
                MainActivity.getMySQLite().insert(new Diary(title, body, date, weather));
                Toast.makeText(this, R.string.write_success, Toast.LENGTH_SHORT).show();

                break;

            case R.id.cancel_btn:
                bodyEdit.setText("");

                break;
        }
    }
}