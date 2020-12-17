package com.example.mydiary;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
   /* public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
*/


    public void connectTest() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/diary_book" +
                "?characterEncoding=UTF-8&useSSL=false";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, "root", "123456");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();


            }
        }


    }
}