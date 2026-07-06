/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author agusseputra
 */
public class DBConnection {
    public static Connection getConnection() throws SQLException{
       String url = "jdbc:mysql://localhost:3306/mhsdb";
       String user = "AlvinFK";
       String password = "Zentify123";
       
//       return DriverManager.getConnection(url, user, password);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver tidak ditemukan.", e);
        }
   } 
}
