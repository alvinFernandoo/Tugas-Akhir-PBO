/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Merepresentasikan satu baris KRS (Kartu Rencana Studi):
 * mahasiswa mengambil satu mata kuliah, diajar satu dosen, pada semester tertentu.
 */
public class KRS {
    private int id;              // primary key di database (0 = belum tersimpan)
    private Student student;
    private Course course;
    private Lecturer lecture;
    private int semester;
    private double score;
    private final String grade;

    public KRS(Course course, double score) {
        this.course = course;
        this.score  = score;
        this.grade  = hitungGrade(score);
    }

    /** Hitung nilai huruf berdasarkan nilai akhir (0-100). */
    private String hitungGrade(double score) {
        if (score >= 85)      return "A";
        else if (score >= 75) return "B";
        else if (score >= 60) return "C";
        else                  return "D";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setLecture(Lecturer lecture) {
        this.lecture = lecture;
    }

    public Lecturer getLecture() {
        return lecture;
    }

    public Course getCourse() {
        return course;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getSemester() {
        return semester;
    }

    public double getScore() {
        return score;
    }

    public String getGrade() {
        return grade;
    }
}
