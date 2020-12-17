package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameEdit, passwordEdit;
    private Button loginBtn;
    private TextView registerText, titleText;
    private ProgressBar loadingBar;

    //
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection conn;
    private final String SERVER_URL = "10.0.2.2";
    private final String DB_Name = "diary_book";
    private final String TABLE_NAME = "user";
    private String url = "jdbc:mysql://" + SERVER_URL + ":3306/" + DB_Name + "?characterEncoding=UTF-8&useSSL=false";

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        loginMySQL("root", "123456");
    }

    private void findView() {
        usernameEdit = findViewById(R.id.username_edit);
        passwordEdit = findViewById(R.id.password_edit);
        loginBtn = findViewById(R.id.login_btn);
        registerText = findViewById(R.id.register_txt);
        titleText = findViewById(R.id.title_text);
        loadingBar = findViewById(R.id.loading_bar);
        loadingBar.setVisibility(View.VISIBLE);

        loginBtn.setOnClickListener(this);
        registerText.setPaintFlags(registerText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void loginMySQL(String username, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    conn = DriverManager.getConnection(url, username, password);
                    System.out.println("Conn:" + conn);
                    loadingBar.setVisibility(View.INVISIBLE);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (conn != null) {
                            usernameEdit.setEnabled(true);
                            passwordEdit.setEnabled(true);
                            loginBtn.setEnabled(true);
                        }
                    }
                });
            }
        }).start();
    }

    public boolean select(String username, String password) {
        String sqlstr = String.format("select username,password from %s where username='%s' " +
                "and password='%s'", TABLE_NAME, username, password);
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlstr);
            if (resultSet != null && resultSet.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void login(String username, String password) {
        final boolean success = select(username, password);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginActivity.this, "登入失敗", Toast.LENGTH_SHORT).show();
                }
                loadingBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        new Thread(){
            @Override
            public void run() {
                login(usernameEdit.getText().toString(), passwordEdit.getText().toString());
            }
        }.start();

        loadingBar.setVisibility(View.VISIBLE);
    }
}