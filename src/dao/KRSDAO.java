/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnection;
import model.Course;
import model.KRS;
import model.Lecturer;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Akses data untuk tabel "krs". Menyimpan relasi mahasiswa (nim),
 * mata kuliah (kodeMatkul), dan dosen (nidn) beserta nilai akhirnya.
 */
public class KRSDAO {
    private Connection connection;

    public KRSDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(KRSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** Cek apakah mahasiswa tersebut sudah pernah mengambil mata kuliah ini. */
    public boolean isAlreadyEnrolled(String nim, String kodeMatkul) {
        String sql = "SELECT COUNT(*) FROM krs WHERE nim = ? AND kodeMatkul = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nim);
            stmt.setString(2, kodeMatkul);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Simpan satu baris KRS baru.
     * @return ID hasil generate database, atau -1 jika gagal.
     */
    public int create(KRS krs) {
        String sql = "INSERT INTO krs (nim, kodeMatkul, nidn, semester, nilai, grade) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, krs.getStudent().getNim());
            stmt.setString(2, krs.getCourse().getCode());
            stmt.setString(3, krs.getLecture().getNidn());
            stmt.setInt(4, krs.getSemester());
            stmt.setDouble(5, krs.getScore());
            stmt.setString(6, krs.getGrade());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
            return -1;
        }
    }

    /** Ambil semua data KRS beserta detail mahasiswa, mata kuliah, dan dosen. */
    public List<KRS> getAll() {
        List<KRS> result = new ArrayList<>();

        String sql =
            "SELECT k.krsID, k.semester, k.nilai, " +
            "       s.cardID AS studentCardID, s.NIM, s.name AS studentName, s.studiProgram, " +
            "       m.Kode, m.Nama AS courseName, m.SKS, m.Semester AS courseSemester, " +
            "       d.cardID AS lecturerCardID, d.NIDN, d.name AS lecturerName, d.Expertise " +
            "FROM krs k " +
            "JOIN students s ON k.nim = s.NIM " +
            "JOIN matkul m ON k.kodeMatkul = m.Kode " +
            "JOIN dosen d ON k.nidn = d.NIDN " +
            "ORDER BY k.krsID";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(mapRowToKRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal mengambil data KRS dari database: " + e.getMessage(), e);
        }
        return result;
    }

    /** Hapus satu baris KRS berdasarkan ID. */
    public int delete(int krsId) {
        String sql = "DELETE FROM krs WHERE krsID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, krsId);
            stmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** Susun ulang satu baris ResultSet menjadi objek KRS yang lengkap (mahasiswa, dosen, mata kuliah). */
    private KRS mapRowToKRS(ResultSet rs) throws SQLException {
        Student student = new Student(
                rs.getString("studentCardID"),
                rs.getString("studentName"),
                rs.getString("NIM"),
                rs.getString("studiProgram"));

        Course course = new Course(
                rs.getString("Kode"),
                rs.getString("courseName"),
                rs.getInt("SKS"),
                rs.getInt("courseSemester"));

        Lecturer lecturer = new Lecturer(
                rs.getString("lecturerCardID"),
                rs.getString("lecturerName"),
                rs.getString("NIDN"),
                rs.getString("Expertise"));

        KRS krs = new KRS(course, rs.getDouble("nilai"));
        krs.setId(rs.getInt("krsID"));
        krs.setStudent(student);
        krs.setLecture(lecturer);
        krs.setSemester(rs.getInt("semester"));
        return krs;
    }
}
