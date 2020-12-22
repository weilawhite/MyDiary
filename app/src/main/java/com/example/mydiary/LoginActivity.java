package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    private boolean registerMode;

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
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerMode) {
                    titleText.setText(R.string.login_text);
                    registerText.setText(R.string.action_register);
                    loginBtn.setText(R.string.action_sign_in);
                } else {
                    titleText.setText(R.string.register_text);
                    registerText.setText(R.string.back);
                    loginBtn.setText(R.string.action_register_in);
                }
                registerMode = !registerMode;
            }
        });
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
                            titleText.setText(R.string.login_text);
                            registerText.setEnabled(true);

                        }
                    }
                });
            }
        }).start();
    }

    public boolean select(String username) {
        String sqlstr = String.format("select username,password from %s where username='%s' ", TABLE_NAME, username);
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
                loadingBar.setVisibility(View.INVISIBLE);
                if (success) {
                    Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("username", username);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            if (conn != null) {
                                try {
                                    conn.close();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }
                            LoginActivity.this.finish();
                        }
                    }.start();
                } else {
                    Toast.makeText(LoginActivity.this, "登入失敗", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onClick(View v) {

        if (conn == null) {
            return;
        }
        final String username = usernameEdit.getText().toString();
        final String password = passwordEdit.getText().toString();

        if (username.equals("") || password.equals("")) {
            Toast.makeText(LoginActivity.this, R.string.input_error, Toast.LENGTH_SHORT).show();
            return;
        }

        loadingBar.setVisibility(View.VISIBLE);

        new Thread() {
            @Override
            public void run() {

                if (registerMode) {
                    register(username, password);
                } else {
                    login(username, password);
                }
            }
        }.start();

        loadingBar.setVisibility(View.VISIBLE);
    }

    public void register(String username, String password) {
        boolean repeat = select(username);
        if (repeat) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingBar.setVisibility(View.INVISIBLE);
                    registerText.setEnabled(true);
                    Toast.makeText(LoginActivity.this, R.string.username_repeat, Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        if (username.equals("") || password.equals("")) return;

        String sqlstr = String.format("insert into %s (username,password) values ('%s','%s');", TABLE_NAME, username, password);

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(sqlstr);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    registerText.setEnabled(true);
                    loadingBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();

                    titleText.setText(R.string.login_text);
                    registerText.setText(R.string.action_register);
                    loginBtn.setText(R.string.action_sign_in);
                    registerMode = false;
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    registerText.setEnabled(true);
                    loadingBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, R.string.register_fail, Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

}