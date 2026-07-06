/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.DosenDAO;
import java.util.List;
import model.Lecturer;

public class DosenController {
     private DosenDAO dosenDAO = new DosenDAO();

    public int create(Lecturer lecturer) {
    if (dosenDAO.isNidnExists(lecturer.getNidn())) {
        throw new IllegalArgumentException("NIDN " + lecturer.getNidn() + " sudah terdaftar!");
    }
    return dosenDAO.create(lecturer);
    }

    public List<Lecturer> getLecturer() {
        return dosenDAO.getLecturer();
    }
    
    public int update(Lecturer lecturer, String nidn) {
    if (!lecturer.getNidn().equals(nidn) && dosenDAO.isNidnExists(lecturer.getNidn())) {
        throw new IllegalArgumentException("NIDN " + lecturer.getNidn() + " sudah terdaftar!");
    }
    return dosenDAO.update(lecturer, nidn);
    }

    public int delete(String nim) {
        return dosenDAO.delete(nim);
    }
    
    
}