/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.KRSDAO;
import java.util.List;
import model.KRS;

/**
 * Mengatur aturan bisnis KRS sebelum data disentuh ke database:
 * - validasi kelengkapan data (mahasiswa, mata kuliah, dosen)
 * - validasi rentang nilai akhir dan semester
 * - mencegah mahasiswa mengambil mata kuliah yang sama dua kali
 */
public class KRSController {

    private static final double NILAI_MIN = 0;
    private static final double NILAI_MAX = 100;
    private static final int SEMESTER_MIN = 1;
    private static final int SEMESTER_MAX = 14;

    private final KRSDAO krsDAO = new KRSDAO();

    /**
     * Simpan KRS baru setelah lolos validasi.
     * @return ID KRS yang baru dibuat.
     * @throws IllegalArgumentException jika data tidak valid atau sudah pernah diambil.
     */
    public int simpan(KRS krs) {
        validasiKelengkapan(krs);
        validasiNilai(krs.getScore());
        validasiSemester(krs.getSemester());

        if (krsDAO.isAlreadyEnrolled(krs.getStudent().getNim(), krs.getCourse().getCode())) {
            throw new IllegalArgumentException(
                    "Mahasiswa " + krs.getStudent().getName() +
                    " sudah mengambil mata kuliah " + krs.getCourse().getCourseName() + ".");
        }

        int generatedId = krsDAO.create(krs);
        if (generatedId <= 0) {
            throw new IllegalStateException("Gagal menyimpan KRS ke database.");
        }
        krs.setId(generatedId);
        return generatedId;
    }

    /** Ambil seluruh data KRS yang sudah tersimpan di database. */
    public List<KRS> getAll() {
        return krsDAO.getAll();
    }

    /** Hapus satu baris KRS. */
    public int hapus(int krsId) {
        if (krsId <= 0) {
            throw new IllegalArgumentException("ID KRS tidak valid.");
        }
        return krsDAO.delete(krsId);
    }

    private void validasiKelengkapan(KRS krs) {
        if (krs.getStudent() == null) {
            throw new IllegalArgumentException("Data mahasiswa belum dipilih.");
        }
        if (krs.getCourse() == null) {
            throw new IllegalArgumentException("Data mata kuliah belum dipilih.");
        }
        if (krs.getLecture() == null) {
            throw new IllegalArgumentException("Data dosen belum dipilih.");
        }
    }

    private void validasiNilai(double nilai) {
        if (nilai < NILAI_MIN || nilai > NILAI_MAX) {
            throw new IllegalArgumentException(
                    "Nilai akhir harus di antara " + (int) NILAI_MIN + " - " + (int) NILAI_MAX + ".");
        }
    }

    private void validasiSemester(int semester) {
        if (semester < SEMESTER_MIN || semester > SEMESTER_MAX) {
            throw new IllegalArgumentException(
                    "Semester harus di antara " + SEMESTER_MIN + " - " + SEMESTER_MAX + ".");
        }
    }
}
