/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseConnect;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoang
 */
public class Dbconnect {

    public static Connection getJDBConnection() {

        String url = "jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "12345678";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try {
                return DriverManager.getConnection(url, user, password);// Thực hiện kết nối đến cơ sở dữ liệu bằng DriverManager   
            } catch (SQLException ex) {
                Logger.getLogger(Dbconnect.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dbconnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static void main(String[] args) {
        Connection conn = getJDBConnection();
        if (conn != null) {
            System.out.println("Success");

        } else {
            System.out.println("Fail");
        }
    }

    
    
    
    public static Connection getConnection() {
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/library_db"; // sửa tên DB nếu cần
        String user = "root"; // hoặc tên user của bạn
        String password = "12345678"; // mật khẩu DB
        return DriverManager.getConnection(url, user, password);
    }   catch (Exception e) {
        e.printStackTrace();
        return null;
    } // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
