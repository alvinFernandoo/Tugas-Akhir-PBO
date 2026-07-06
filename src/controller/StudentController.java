/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.Student;
import dao.StudentDAO;

/**
 *
 * @author agusseputra
 */
public class StudentController {
     private StudentDAO studentDAO = new StudentDAO();

    public int create(Student student) {
    if (studentDAO.isNimExists(student.getNim())) {
        throw new IllegalArgumentException("NIM " + student.getNim() + " sudah terdaftar!");
    }
    return studentDAO.create(student);
    }

    public List<Student> getStudent() {
        return studentDAO.getStudent();
    }
    
    public int update(Student student, String nim) {
    if (!student.getNim().equals(nim) && studentDAO.isNimExists(student.getNim())) {
        throw new IllegalArgumentException("NIM " + student.getNim() + " sudah terdaftar!");
    }
    return studentDAO.update(student, nim);
    }

    public int delete(String nim) {
        return studentDAO.delete(nim);
    }
    
    
}
