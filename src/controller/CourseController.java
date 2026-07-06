/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CourseDAO;
import java.util.List;
import model.Course;

public class CourseController {
     
    private CourseDAO courseDAO = new CourseDAO();

    public int create(Course course) {
        if (courseDAO.isKodeExists(course.getCode())) {
            throw new IllegalArgumentException("Kode mata kuliah " + course.getCode() + " sudah terdaftar!");
        }
        return courseDAO.create(course);
    }

    public List<Course> getCourse() {
        return courseDAO.getCourse();
    }
    
    public int update(Course course, String code) {
        if (!course.getCode().equals(code) && courseDAO.isKodeExists(course.getCode())) {
            throw new IllegalArgumentException("Kode mata kuliah " + course.getCode() + " sudah terdaftar!");
        }
        return courseDAO.update(course, code);
    }

    public int delete(String code) {
        return courseDAO.delete(code);
    }
    
    
}
