/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import config.DBConnection;
import model.Lecturer;


public class DosenDAO {
    private Connection connection;
    public DosenDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DosenDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // create User
    public int create(Lecturer lecturer){
        try{
            String sql = "INSERT INTO dosen (CardID, NIDN, name, Expertise) VALUES(?,?,?,?)";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, lecturer.getidCard());   
            stmt.setString(2, lecturer.getNidn());    
            stmt.setString(3, lecturer.getName());     
            stmt.setString(4, lecturer.getExpertise()); 
            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
            System.out.println("ERROR: " + e.getMessage());  
            return 0;
        }
        
    }
    
    public boolean isNidnExists(String nidn){
        String sql = "SELECT COUNT(*) FROM dosen WHERE NIDN = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nidn);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
            }
    }
    
     // Select/read Users
    public List<Lecturer> getLecturer(){
        List<Lecturer> lecturer = new ArrayList<>();
        
        try{
            String sql = "SELECT * FROM dosen";
            PreparedStatement stmt =connection.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("lectureID");
                String cardID = rs.getString("cardID");
                String NIDN = rs.getString("NIDN");
                String name = rs.getString("name");
                String expertise = rs.getString("Expertise");
                
                lecturer.add(new Lecturer(cardID,name,NIDN,expertise));
                
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lecturer;
    }
    // update student
    public int update(Lecturer lecturer, String nidn){
        try{
            String sql = "UPDATE dosen SET cardID=?,  name=?, Expertise=? WHERE NIDN=?";
            PreparedStatement stmt =connection.prepareStatement(sql);
           stmt.setString(1, lecturer.getidCard());
            stmt.setString(2, lecturer.getNidn());
            stmt.setString(3, lecturer.getName());
            stmt.setString(4, lecturer.getExpertise());
            stmt.executeUpdate();
            return 1;            
        }catch(SQLException e){
              return 0;
        }
        
    }
     // Delete User
    public int delete(String nidn){
        try{
            String sql = "DELETE FROM dosen  WHERE NIDN=?";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, nidn);
            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
              //e.printStackTrace();
              return 0;
        }
        
    }
    
}
