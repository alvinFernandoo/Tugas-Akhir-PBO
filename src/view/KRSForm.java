/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.CourseController;
import controller.DosenController;
import controller.StudentController;
import controller.KRSController;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Course;
import model.KRS;
import model.Lecturer;
import model.Student;

public class KRSForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(KRSForm.class.getName());

    private StudentController studentController = new StudentController();
    private DosenController   dosenController   = new DosenController();
    private CourseController  courseController  = new CourseController();
    private KRSController     krsController     = new KRSController();
    
    private static final int PAGE_SIZE = 5;

    // Daftar KRS yang sudah diinput
    private java.util.List<KRS> krsList = new java.util.ArrayList<>();
    
    private int studentPage  = 0;
    private int lecturerPage = 0;
    private int coursePage   = 0;
    private int krsPage = 0;
    
    private java.util.List<KRS> krsFilteredList = new java.util.ArrayList<>();
    
    // Cache list dari database (untuk lookup berdasarkan index ComboBox)
    private java.util.List<Student>  studentList  = new java.util.ArrayList<>();
    private java.util.List<Lecturer> lecturerList = new java.util.ArrayList<>();
    private java.util.List<Course>   courseList   = new java.util.ArrayList<>();

    // Objek yang sedang dipilih
    private Student  selectedStudent  = null;
    private Lecturer selectedLecturer = null;
    private Course   selectedCourse   = null;

    public KRSForm() {
        initComponents();
        setupNilaiListeners();
        setupPaginationListeners();
        loadAllData();
    }
    
    private void setupPaginationListeners() {
        // Panel Mahasiswa
        jButton5.addActionListener(e -> nextStudentPage());     // ">>"
        jButton6.addActionListener(e -> previousStudentPage()); // "<<"

        // Panel Dosen
        jButton3.addActionListener(e -> nextLecturerPage());     // ">>"
        jButton4.addActionListener(e -> previousLecturerPage()); // "<<"

        // Panel Mata Kuliah
        jButton1.addActionListener(e -> nextCoursePage());     // ">>"
        jButton2.addActionListener(e -> previousCoursePage()); // "<<"
    
        jButtonNext.addActionListener(e -> nextKRSPage());         // ">>"
        jButtonPrevious.addActionListener(e -> previousKRSPage()); // "<<"
        jTextFieldSKRS.addActionListener(e -> searchKRS());        // Enter di field pencar
    
    }
    
    
    private void setupNilaiListeners() {
        javax.swing.event.DocumentListener listener = new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { hitungNilai(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { hitungNilai(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { hitungNilai(); }
        };
        jTextFieldTugas.getDocument().addDocumentListener(listener);
        jTextFieldUTS.getDocument().addDocumentListener(listener);
        jTextFieldUAS.getDocument().addDocumentListener(listener);
    }
    
    private void loadAllData() {
        loadStudentData();
        loadLecturerData();
        loadCourseData();
        loadKRSData();
    }
    
    private void loadStudentData() {
        studentList = studentController.getStudent();

        // Isi ComboBox hanya dengan nama (String) — hindari incompatible types
        jComboBoxNamaMhs.removeAllItems();
        for (Student s : studentList) {
            jComboBoxNamaMhs.addItem(s.getName());
        }
        // Pilih item pertama secara eksplisit agar onComboBox terpanggil
        if (!studentList.isEmpty()) {
            jComboBoxNamaMhs.setSelectedIndex(0);
            onComboBoxMahasiswaChanged();
        }

        // Isi tabel daftar mahasiswa
        String[][] data = new String[studentList.size()][2];
        for (int i = 0; i < studentList.size(); i++) {
            data[i][0] = studentList.get(i).getName();
            data[i][1] = studentList.get(i).getNim();
        }
        jTableStudent.setModel(new DefaultTableModel(data, new String[]{"Nama", "NIM"}));
    }
    
    private void loadLecturerData() {
        lecturerList = dosenController.getLecturer();

        jComboBoxNamaDosen.removeAllItems();
        for (Lecturer l : lecturerList) {
            jComboBoxNamaDosen.addItem(l.getName());
        }
        if (!lecturerList.isEmpty()) {
            jComboBoxNamaDosen.setSelectedIndex(0);
            onComboBoxDosenChanged();
        }

        String[][] data = new String[lecturerList.size()][2];
        for (int i = 0; i < lecturerList.size(); i++) {
            data[i][0] = lecturerList.get(i).getName();
            data[i][1] = lecturerList.get(i).getNidn();
        }
        jTableLecture.setModel(new DefaultTableModel(data, new String[]{"Nama", "NIDN"}));
    }
    
    private void loadCourseData() {
        courseList = courseController.getCourse();

        jComboBox3.removeAllItems();
        for (Course c : courseList) {
            jComboBox3.addItem(c.getCourseName());
        }
        if (!courseList.isEmpty()) {
            jComboBox3.setSelectedIndex(0);
            onComboBoxCourseChanged();
        }

        String[][] data = new String[courseList.size()][2];
        for (int i = 0; i < courseList.size(); i++) {
            data[i][0] = courseList.get(i).getCourseName();
            data[i][1] = courseList.get(i).getCode();
        }
        jTableCourse.setModel(new DefaultTableModel(data, new String[]{"Nama", "Kode"}));
    }
    
     private void onComboBoxMahasiswaChanged() {
        int idx = jComboBoxNamaMhs.getSelectedIndex();
        if (idx >= 0 && idx < studentList.size()) {
            selectedStudent = studentList.get(idx);
            jTextFieldNIM.setText(selectedStudent.getNim());
            jTextFieldProdi.setText(selectedStudent.getStudyProgram());
        }
    }
     
     private void onComboBoxDosenChanged() {
        int idx = jComboBoxNamaDosen.getSelectedIndex();
        if (idx >= 0 && idx < lecturerList.size()) {
            selectedLecturer = lecturerList.get(idx);
            jTextFieldNIDN.setText(selectedLecturer.getNidn());
            jTextFieldExpert.setText(selectedLecturer.getExpertise());
        }
    }
     
    private void onComboBoxCourseChanged() {
        int idx = jComboBox3.getSelectedIndex();
        if (idx >= 0 && idx < courseList.size()) {
            selectedCourse = courseList.get(idx);
            jTextFieldKode.setText(selectedCourse.getCode());
            jTextFieldMatkulSKS.setText(String.valueOf(selectedCourse.getSKS()));
            jTextFieldSKS.setText(String.valueOf(selectedCourse.getSKS()));
        }
    }
     
    private void hitungNilai() {
        try {
            String t2 = jTextFieldTugas.getText().trim();
            String t3 = jTextFieldUTS.getText().trim();
            String t4 = jTextFieldUAS.getText().trim();

            if (t2.isEmpty() || t3.isEmpty() || t4.isEmpty()) {
                return;
            }

            // Ganti koma ke titik agar parseDouble selalu berhasil di semua locale
            double tugas = Double.parseDouble(t2.replace(",", "."));
            double uts   = Double.parseDouble(t3.replace(",", "."));
            double uas   = Double.parseDouble(t4.replace(",", "."));

            // Validasi rentang 0-100
            if (tugas < 0 || tugas > 100 || uts < 0 || uts > 100 || uas < 0 || uas > 100) {
                JOptionPane.showMessageDialog(this, "Nilai harus berada di antara 0 - 100!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                jTextFieldNilai.setText("-");
                jTextFieldHuruf.setText("-");
                return;
            }

            // Rumus: 20% Tugas + 30% UTS + 50% UAS
            double nilaiAkhir = (0.20 * tugas) + (0.30 * uts) + (0.50 * uas);

            // Simpan selalu dengan titik sebagai pemisah desimal (locale-safe)
            jTextFieldNilai.setText(String.format(java.util.Locale.US, "%.1f", nilaiAkhir));

            // Hitung huruf
            String huruf;
            if (nilaiAkhir >= 85)      huruf = "A";
            else if (nilaiAkhir >= 75) huruf = "B";
            else if (nilaiAkhir >= 60) huruf = "C";
            else                       huruf = "D";

            jTextFieldHuruf.setText(huruf);

        } catch (NumberFormatException ex) {
            jTextFieldNilai.setText("-");
            jTextFieldHuruf.setText("-");
            JOptionPane.showMessageDialog(this, "Nilai harus berupa angka! Gunakan titik atau koma sebagai desimal.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadKRSData() {
        krsList.clear();
 
        try {
            krsList.addAll(krsController.getAll());
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data KRS: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
 
        krsFilteredList = new java.util.ArrayList<>(krsList);
        krsPage = 0;
        renderKRSTablePage();
    }
    
    private void renderKRSTablePage() {
        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.setRowCount(0);
 
        int from = krsPage * PAGE_SIZE;
        int to   = Math.min(from + PAGE_SIZE, krsFilteredList.size());
 
        for (int i = from; i < to; i++) {
            KRS krs = krsFilteredList.get(i);
            model.addRow(new Object[]{
                krs.getStudent().getName(),
                krs.getCourse().getCourseName(),
                krs.getLecture().getName(),
                krs.getCourse().getSKS(),
                krs.getSemester(),
                krs.getGrade()
            });
        }
    }
    
    private void nextKRSPage() {
        if ((krsPage + 1) * PAGE_SIZE >= krsFilteredList.size()) {
            JOptionPane.showMessageDialog(this, "Sudah berada di halaman terakhir.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        krsPage++;
        renderKRSTablePage();
    }
    
    private void previousKRSPage() {
        if (krsPage == 0) {
            JOptionPane.showMessageDialog(this, "Sudah berada di halaman pertama.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        krsPage--;
        renderKRSTablePage();
    }
    
    private void searchKRS() {
        String keyword = jTextFieldSKRS.getText().trim().toLowerCase();
 
        if (keyword.isEmpty()) {
            krsFilteredList = new java.util.ArrayList<>(krsList);
        } else {
            krsFilteredList = new java.util.ArrayList<>();
            for (KRS krs : krsList) {
                boolean cocok =
                        krs.getStudent().getName().toLowerCase().contains(keyword) ||
                        krs.getStudent().getNim().toLowerCase().contains(keyword) ||
                        krs.getCourse().getCourseName().toLowerCase().contains(keyword) ||
                        krs.getCourse().getCode().toLowerCase().contains(keyword) ||
                        krs.getLecture().getName().toLowerCase().contains(keyword);
                if (cocok) {
                    krsFilteredList.add(krs);
                }
            }
        }
 
        krsPage = 0;
        renderKRSTablePage();
 
        if (krsFilteredList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data KRS tidak ditemukan.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
     
    private void tambahBarisTabelKRS(KRS krs) {
        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.addRow(new Object[]{
            krs.getStudent().getName(),
            krs.getCourse().getCourseName(),
            krs.getLecture().getName(),
            krs.getCourse().getSKS(),
            krs.getSemester(),
            krs.getGrade()
        });
    }
     
    private void simpanKRS() {
        if (selectedStudent == null || selectedLecturer == null || selectedCourse == null) {
            JOptionPane.showMessageDialog(this,
                    "Lengkapi data Mahasiswa, Dosen, dan Mata Kuliah terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
    }
 
        String nilaiStr = jTextFieldNilai.getText().trim();
        String semStr   = jTextFieldSemester.getText().trim();
 
        if (nilaiStr.equals("-") || nilaiStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan nilai Tugas, UTS, dan UAS terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        try {
            // Ganti koma ke titik agar parseDouble aman di semua locale
            double nilaiAkhir = Double.parseDouble(nilaiStr.replace(",", "."));
 
            // Semester: ambil dari field jika diisi, kalau kosong ambil dari data mata kuliah
            int semester;
            if (semStr.isEmpty()) {
                semester = selectedCourse.getSemester();
                jTextFieldSemester.setText(String.valueOf(semester)); // tampilkan ke field
            } else {
                semester = Integer.parseInt(semStr);
            }
 
            KRS krs = new KRS(selectedCourse, nilaiAkhir);
            krs.setStudent(selectedStudent);
            krs.setLecture(selectedLecturer);
            krs.setSemester(semester);
 
            // Validasi bisnis (rentang nilai, rentang semester, duplikasi) ada di KRSController
            krsController.simpan(krs);
 
            loadKRSData();
 
            JOptionPane.showMessageDialog(this, "KRS berhasil disimpan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
 
            clearInputNilai();
 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Format semester tidak valid! Semester harus berupa angka bulat (contoh: 1, 2, 3).",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
        
    private void hapusKRS() {
        int row = jTable6.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris KRS yang ingin dihapus!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Hapus data KRS ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
 
        try {
            int actualIndex = krsPage * PAGE_SIZE + row;
            KRS krs = krsFilteredList.get(actualIndex);
            krsController.hapus(krs.getId());
            krsList.remove(krs);
            loadKRSData();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clearInputNilai() {
        jTextFieldTugas.setText("");
        jTextFieldUTS.setText("");
        jTextFieldUAS.setText("");
        jTextFieldNilai.setText("-");
        jTextFieldHuruf.setText("-");
    }
    
    private void renderStudentTablePage() {
        int from = studentPage * PAGE_SIZE;
        int to   = Math.min(from + PAGE_SIZE, studentList.size());

        String[][] data = new String[Math.max(0, to - from)][2];
        for (int i = from; i < to; i++) {
            data[i - from][0] = studentList.get(i).getName();
            data[i - from][1] = studentList.get(i).getNim();
        }
        jTableStudent.setModel(new DefaultTableModel(data, new String[]{"Nama", "NIM"}));
    }

    /** Tombol ">>" pada panel Mahasiswa: pindah ke halaman selanjutnya jika tersedia */
    private void nextStudentPage() {
        if ((studentPage + 1) * PAGE_SIZE >= studentList.size()) {
            JOptionPane.showMessageDialog(this, "Sudah berada di halaman terakhir.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        studentPage++;
        renderStudentTablePage();
    }

    /** Tombol "<<" pada panel Mahasiswa: kembali ke halaman sebelumnya jika tersedia */
    private void previousStudentPage() {
        if (studentPage == 0) {
            JOptionPane.showMessageDialog(this, "Sudah berada di halaman pertama.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        studentPage--;
        renderStudentTablePage();
    }
     
     private void renderLecturerTablePage() {
        int from = lecturerPage * PAGE_SIZE;
        int to   = Math.min(from + PAGE_SIZE, lecturerList.size());

        String[][] data = new String[Math.max(0, to - from)][2];
        for (int i = from; i < to; i++) {
            data[i - from][0] = lecturerList.get(i).getName();
            data[i - from][1] = lecturerList.get(i).getNidn();
        }
        jTableLecture.setModel(new DefaultTableModel(data, new String[]{"Nama", "NIDN"}));
    }

    /** Tombol ">>" pada panel Dosen: pindah ke halaman selanjutnya jika tersedia */
    private void nextLecturerPage() {
        if ((lecturerPage + 1) * PAGE_SIZE >= lecturerList.size()) {
            JOptionPane.showMessageDialog(this, "Sudah berada di halaman terakhir.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        lecturerPage++;
        renderLecturerTablePage();
    }

    /** Tombol "<<" pada panel Dosen: kembali ke halaman sebelumnya jika tersedia */
    private void previousLecturerPage() {
        if (lecturerPage == 0) {
            JOptionPane.showMessageDialog(this, "Sudah berada di halaman pertama.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        lecturerPage--;
        renderLecturerTablePage();
    }
    
    private void renderCourseTablePage() {
        int from = coursePage * PAGE_SIZE;
        int to   = Math.min(from + PAGE_SIZE, courseList.size());

        String[][] data = new String[Math.max(0, to - from)][2];
        for (int i = from; i < to; i++) {
            data[i - from][0] = courseList.get(i).getCourseName();
            data[i - from][1] = courseList.get(i).getCode();
        }
        jTableCourse.setModel(new DefaultTableModel(data, new String[]{"Nama", "Kode"}));
    }

    /** Tombol ">>" pada panel Mata Kuliah: pindah ke halaman selanjutnya jika tersedia */
    private void nextCoursePage() {
        if ((coursePage + 1) * PAGE_SIZE >= courseList.size()) {
            JOptionPane.showMessageDialog(this, "Sudah berada di halaman terakhir.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        coursePage++;
        renderCourseTablePage();
    }

    /** Tombol "<<" pada panel Mata Kuliah: kembali ke halaman sebelumnya jika tersedia */
    private void previousCoursePage() {
        if (coursePage == 0) {
            JOptionPane.showMessageDialog(this, "Sudah berada di halaman pertama.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        coursePage--;
        renderCourseTablePage();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableLecture = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxNamaDosen = new javax.swing.JComboBox<>();
        jTextFieldNIDN = new javax.swing.JTextField();
        jTextFieldExpert = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableStudent = new javax.swing.JTable();
        jComboBoxNamaMhs = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldProdi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldNIM = new javax.swing.JTextField();
        jTextFieldSemester = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableCourse = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldKode = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldMatkulSKS = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldTugas = new javax.swing.JTextField();
        jTextFieldUTS = new javax.swing.JTextField();
        jTextFieldUAS = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldSKS = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldHuruf = new javax.swing.JTextField();
        jTextFieldNilai = new javax.swing.JTextField();
        jButtonSimpan = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldSKRS = new javax.swing.JTextField();
        jButtonSKRS = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jButtonPrevious = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuDashbord = new javax.swing.JMenu();
        jMenuStudent = new javax.swing.JMenu();
        jMenuLecture = new javax.swing.JMenu();
        jMenuCourse = new javax.swing.JMenu();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dosen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 14))); // NOI18N

        jTableLecture.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nama", "NIDN"
            }
        ));
        jScrollPane2.setViewportView(jTableLecture);

        jLabel6.setText("Nama");

        jLabel7.setText("NIDN");

        jLabel8.setText("Expertise");

        jComboBoxNamaDosen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxNamaDosen.addActionListener(this::jComboBoxNamaDosenActionPerformed);

        jButton3.setText(">>");

        jButton4.setText("<<");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxNamaDosen, 0, 104, Short.MAX_VALUE)
                    .addComponent(jTextFieldNIDN)
                    .addComponent(jTextFieldExpert))
                .addGap(33, 33, 33))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(57, 57, 57))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxNamaDosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldNIDN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldExpert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(5, 5, 5))
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mahasiswa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 14))); // NOI18N

        jTableStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nama", "NIM"
            }
        ));
        jScrollPane4.setViewportView(jTableStudent);

        jComboBoxNamaMhs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxNamaMhs.addActionListener(this::jComboBoxNamaMhsActionPerformed);

        jLabel2.setText("Nama");

        jLabel3.setText("NIM");

        jLabel4.setText("Prodi");

        jLabel5.setText("Semester");

        jButton5.setText(">>");

        jButton6.setText("<<");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxNamaMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldProdi)
                            .addComponent(jTextFieldNIM)
                            .addComponent(jTextFieldSemester, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))))
                .addGap(38, 38, 38))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addGap(54, 54, 54))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxNamaMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldNIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldProdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mata Kuliah", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 14))); // NOI18N

        jTableCourse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nama", "Kode"
            }
        ));
        jScrollPane3.setViewportView(jTableCourse);

        jLabel9.setText("Nama");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addActionListener(this::jComboBox3ActionPerformed);

        jLabel10.setText("Kode");

        jLabel11.setText("SKS");

        jButton1.setText(">>");

        jButton2.setText("<<");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldKode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMatkulSKS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(54, 54, 54))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldMatkulSKS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1)))
        );

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel1.setText("FORM KRS");

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nama", "Mata Kuliah", "Dosen", "SKS", "Semester", "Nilai"
            }
        ));
        jScrollPane6.setViewportView(jTable6);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input Nilai", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 14))); // NOI18N

        jLabel12.setText("Nilai(T/UTS/UAS)");

        jTextFieldUTS.addActionListener(this::jTextFieldUTSActionPerformed);

        jLabel13.setText("SKS");

        jTextFieldSKS.setText("-");

        jLabel14.setText("Nilai");

        jLabel15.setText("Nilai Huruf");

        jTextFieldHuruf.setText("-");

        jTextFieldNilai.setText("-");

        jButtonSimpan.setBackground(new java.awt.Color(51, 255, 51));
        jButtonSimpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSimpan.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSimpan.setText("Simpan");
        jButtonSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSimpanMouseClicked(evt);
            }
        });

        jButtonDelete.setBackground(new java.awt.Color(255, 0, 51));
        jButtonDelete.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonDelete.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDelete.setText("Delete");
        jButtonDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDeleteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldTugas, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUTS, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUAS, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldSKS, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHuruf, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNilai, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonDelete)
                .addGap(18, 18, 18)
                .addComponent(jButtonSimpan)
                .addGap(67, 67, 67))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldTugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldUTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldUAS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSKS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldHuruf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldNilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSimpan)
                    .addComponent(jButtonDelete))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel16.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel16.setText("Search : ");

        jTextFieldSKRS.addActionListener(this::jTextFieldSKRSActionPerformed);

        jButtonSKRS.setText("Search");
        jButtonSKRS.addActionListener(this::jButtonSKRSActionPerformed);

        jButtonNext.setText(">>");

        jButtonPrevious.setText("<<");

        jMenuDashbord.setText("Dashbord");
        jMenuDashbord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuDashbordMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuDashbord);

        jMenuStudent.setText("AddStudent");
        jMenuStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuStudentMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuStudent);

        jMenuLecture.setText("AddLecture");
        jMenuLecture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuLectureMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuLecture);

        jMenuCourse.setText("AddCourse");
        jMenuCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuCourseMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuCourse);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldSKRS, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addComponent(jButtonSKRS)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(387, 387, 387)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(jTextFieldSKRS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonNext)
                                    .addComponent(jButtonPrevious)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jButtonSKRS)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldUTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUTSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUTSActionPerformed

    private void jMenuDashbordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuDashbordMouseClicked
        // TODO add your handling code here:
        Dashbord dashbord = new Dashbord();
        dashbord.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuDashbordMouseClicked

    private void jMenuStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuStudentMouseClicked
        // TODO add your handling code here:
        StudentForm studentForm = null;
        try {
            studentForm = new StudentForm();
        } catch (SQLException ex) {
            System.getLogger(KRSForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        studentForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuStudentMouseClicked

    private void jMenuLectureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuLectureMouseClicked
        // TODO add your handling code here:
        LectureForm lectureForm = null;
        try {
            lectureForm = new LectureForm();
        } catch (SQLException ex) {
            System.getLogger(KRSForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        lectureForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuLectureMouseClicked

    private void jMenuCourseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuCourseMouseClicked
        // TODO add your handling code here:
        CourseForm courseForm = null;
        courseForm = new CourseForm();
        courseForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuCourseMouseClicked

    private void jButtonSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSimpanMouseClicked
        // TODO add your handling code here:
        simpanKRS();
    }//GEN-LAST:event_jButtonSimpanMouseClicked

    private void jButtonDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDeleteMouseClicked
        // TODO add your handling code here:
        hapusKRS();
    }//GEN-LAST:event_jButtonDeleteMouseClicked

    private void jComboBoxNamaMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNamaMhsActionPerformed
        // TODO add your handling code here:
        onComboBoxMahasiswaChanged();
    }//GEN-LAST:event_jComboBoxNamaMhsActionPerformed

    private void jComboBoxNamaDosenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNamaDosenActionPerformed
        // TODO add your handling code here:
        onComboBoxDosenChanged();
    }//GEN-LAST:event_jComboBoxNamaDosenActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        onComboBoxCourseChanged();
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButtonSKRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSKRSActionPerformed
        // TODO add your handling code here:
        searchKRS();
    }//GEN-LAST:event_jButtonSKRSActionPerformed

    private void jTextFieldSKRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSKRSActionPerformed
        // TODO add your handling code here:
        searchKRS();
    }//GEN-LAST:event_jTextFieldSKRSActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new KRSForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPrevious;
    private javax.swing.JButton jButtonSKRS;
    private javax.swing.JButton jButtonSimpan;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBoxNamaDosen;
    private javax.swing.JComboBox<String> jComboBoxNamaMhs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuCourse;
    private javax.swing.JMenu jMenuDashbord;
    private javax.swing.JMenu jMenuLecture;
    private javax.swing.JMenu jMenuStudent;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTableCourse;
    private javax.swing.JTable jTableLecture;
    private javax.swing.JTable jTableStudent;
    private javax.swing.JTextField jTextFieldExpert;
    private javax.swing.JTextField jTextFieldHuruf;
    private javax.swing.JTextField jTextFieldKode;
    private javax.swing.JTextField jTextFieldMatkulSKS;
    private javax.swing.JTextField jTextFieldNIDN;
    private javax.swing.JTextField jTextFieldNIM;
    private javax.swing.JTextField jTextFieldNilai;
    private javax.swing.JTextField jTextFieldProdi;
    private javax.swing.JTextField jTextFieldSKRS;
    private javax.swing.JTextField jTextFieldSKS;
    private javax.swing.JTextField jTextFieldSemester;
    private javax.swing.JTextField jTextFieldTugas;
    private javax.swing.JTextField jTextFieldUAS;
    private javax.swing.JTextField jTextFieldUTS;
    // End of variables declaration//GEN-END:variables
}
