/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import config.DBConnection;


public class CourseDAO {
    private Connection connection;
    public CourseDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // create User
    public int create(Course course){
        try{
            String sql = "INSERT INTO matkul (Nama, Kode, SKS, Semester) VALUES(?,?,?,?)";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, course.getCourseName());   
            stmt.setString(2, course.getCode());    
            stmt.setInt(3, course.getSKS());     
            stmt.setInt(4, course.getSemester()); 
            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
            System.out.println("ERROR: " + e.getMessage());  
            return 0;
        }
        
    }
    
    public boolean isKodeExists(String kode){
        String sql = "SELECT COUNT(*) FROM matkul WHERE Kode = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, kode);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
        throw new RuntimeException(e);
            }
    }
    
     // Select/read Users
    public List<Course> getCourse(){
        List<Course> course = new ArrayList<>();
        
        try{
            String sql = "SELECT * FROM matkul";
            PreparedStatement stmt =connection.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("CourseID");
                String Nama = rs.getString("Nama");
                String Kode = rs.getString("Kode");
                int SKS = rs.getInt("SKS");
                int Semester = rs.getInt("Semester");
                
                course.add(new Course(Kode, Nama, SKS, Semester));
                
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return course;
    }
    // update student
    public int update(Course course, String code){
        try{
            String sql = "UPDATE matkul SET Nama=?,  SKS=?, Semester=? WHERE Kode=?";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, course.getCourseName()); // Nama
            stmt.setInt(2, course.getSKS());           // SKS
            stmt.setInt(3, course.getSemester());      // Semester
            stmt.setString(4, code);                   // WHERE Kode=?
            stmt.executeUpdate();
            return 1;            
        }catch(SQLException e){
              return 0;
        }
        
    }
     // Delete User
    public int delete(String code){
        try{
            String sql = "DELETE FROM matkul  WHERE Kode=?";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, code);
            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
              //e.printStackTrace();
              return 0;
        }
        
    }
    
}    

