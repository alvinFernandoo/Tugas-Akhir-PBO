/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lecturer;
import controller.DosenController;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class LectureForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LectureForm.class.getName());
    
    private String selectedNIDN;
    DosenController controller = new DosenController();

    public LectureForm() throws SQLException {
        initComponents();
        loadLecturerData();
    }   
    
    @SuppressWarnings("unchecked")
    private void loadLecturerData() {
        List<Lecturer> lecturers = controller.getLecturer();
        String[][] data = new String[lecturers.size()][4];
 
        for (int i = 0; i < lecturers.size(); i++) {
            data[i][0] = lecturers.get(i).getidCard();
            data[i][1] = lecturers.get(i).getName();
            data[i][2] = lecturers.get(i).getNidn();
            data[i][3] = lecturers.get(i).getExpertise();
        }
 
        String[] columnNames = {"ID", "Nama", "NIDN", "Expertise"};
        jTableLecture.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldExpert = new javax.swing.JTextField();
        jTextFieldNama = new javax.swing.JTextField();
        jTextFieldNIDN = new javax.swing.JTextField();
        jButtonSimpan = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldNIK = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableLecture = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldLecture = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuDashbor = new javax.swing.JMenu();
        jMenuStudent = new javax.swing.JMenu();
        jMenuCourse = new javax.swing.JMenu();
        jMenuKRS = new javax.swing.JMenu();

        jTextField3.addActionListener(this::jTextField3ActionPerformed);

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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 14))); // NOI18N

        jLabel1.setText("Nama");

        jLabel3.setText("Expert");

        jLabel4.setText("NIDN");

        jTextFieldExpert.addActionListener(this::jTextFieldExpertActionPerformed);

        jTextFieldNama.addActionListener(this::jTextFieldNamaActionPerformed);

        jTextFieldNIDN.addActionListener(this::jTextFieldNIDNActionPerformed);

        jButtonSimpan.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButtonSimpan.setText("Simpan");
        jButtonSimpan.addActionListener(this::jButtonSimpanActionPerformed);

        jButton3.setText("Batal");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 51));
        jButton2.setText("Delete");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jLabel7.setText("NIK");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextFieldNama, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldNIDN)
                                    .addComponent(jTextFieldExpert)
                                    .addComponent(jTextFieldNIK, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldNIK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldNIDN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldExpert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel2.setText("FORM DOSEN");

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TabelDosen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial Black", 1, 14))); // NOI18N

        jTableLecture.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableLecture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLectureMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableLecture);

        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setText(">>");

        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setText("<<");

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel6.setText("Search Data : ");

        jTextFieldLecture.addActionListener(this::jTextFieldLectureActionPerformed);

        jButtonSearch.setText("Search");
        jButtonSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSearchMouseClicked(evt);
            }
        });
        jButtonSearch.addActionListener(this::jButtonSearchActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldLecture, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSearch)
                        .addContainerGap(29, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldLecture, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jMenuDashbor.setText("Dashbord");
        jMenuDashbor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuDashborMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuDashbor);

        jMenuStudent.setText("AddStudent");
        jMenuStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuStudentMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuStudent);

        jMenuCourse.setText("AddCourse");
        jMenuCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuCourseMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuCourse);

        jMenuKRS.setText("AddKRS");
        jMenuKRS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuKRSMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuKRS);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void clearForm() {
        jTextFieldNama.setText("");
        jTextFieldExpert.setText("");
        jTextFieldNIDN.setText("");
        jTextFieldNIK.setText("");
        jTextFieldNIDN.setEditable(true);
        selectedNIDN = null;
    }      
    
    private void jTextFieldExpertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldExpertActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldExpertActionPerformed

    private void jTextFieldNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNamaActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextFieldNIDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNIDNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNIDNActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to delete this Lecture?",
        "Delete Confirmation",
        JOptionPane.YES_NO_OPTION
            );

            if(confirm == JOptionPane.YES_OPTION) {
                // Proses delete
                try {
                    controller.delete(selectedNIDN);
                    JOptionPane.showMessageDialog(this,"Lecture deleted successfully!");
                    this.selectedNIDN=null;
                    this.clearForm();
                    loadLecturerData();
                } catch (Exception ex) {
                    Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this,"deleted unsuccessfully!");
                }
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButtonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSimpanActionPerformed
        // TODO add your handling code here:
        String name = jTextFieldNama.getText();
        String NIK = jTextFieldNIK.getText();
        String NIDN = jTextFieldNIDN.getText();
        String expertise = jTextFieldExpert.getText();
        
        if(name.isEmpty() || NIDN.isEmpty() || expertise.isEmpty() || NIK.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill in all fields ", "Input Error", JOptionPane.ERROR_MESSAGE);
        }else {
            Lecturer lecturer = new Lecturer(NIK,name,NIDN,expertise);
            try {
               
                int res;
                if(selectedNIDN != null){
               
                    res = controller.update(lecturer,selectedNIDN);
                }
                else{
                    res = controller.create(lecturer);
                }
                if (res == 1) {
                    JOptionPane.showMessageDialog(this, "User created successfully ", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.selectedNIDN=null;
                    this.clearForm();
                    loadLecturerData();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Error occurred while inserting ", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonSimpanActionPerformed

    private void jTextFieldLectureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLectureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLectureActionPerformed

    private void jTableLectureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLectureMouseClicked
        // TODO add your handling code here:
        int selectedRow =jTableLecture.getSelectedRow();
        if(selectedRow != -1){
            if(jTableLecture.getValueAt(selectedRow, 2) == null) return;
            jTextFieldNIDN.setEditable(false);
            selectedNIDN = jTableLecture.getValueAt(selectedRow, 2).toString();
            jTextFieldNIK.setText(jTableLecture.getValueAt(selectedRow, 0).toString());
            jTextFieldNama.setText(jTableLecture.getValueAt(selectedRow, 1).toString());
            jTextFieldNIDN.setText(jTableLecture.getValueAt(selectedRow, 2).toString());
            jTextFieldExpert.setText(jTableLecture.getValueAt(selectedRow, 3).toString());
        }
    }//GEN-LAST:event_jTableLectureMouseClicked

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        // TODO add your handling code here:
        String keyword = jTextFieldLecture.getText().toLowerCase();
        List<Lecturer> all = controller.getLecturer();
        List<Lecturer> filtered = new java.util.ArrayList<>();
    
        for (Lecturer l : all) {
            if (l.getName().toLowerCase().contains(keyword) ||
                l.getNidn().toLowerCase().contains(keyword) ||
                l.getExpertise().toLowerCase().contains(keyword) ||
                l.getidCard().toLowerCase().contains(keyword)) {  // ← tambahkan ini
                filtered.add(l);
                }
        }
    
        String[][] data = new String[filtered.size()][4];
        for (int i = 0; i < filtered.size(); i++) {
            data[i][0] = filtered.get(i).getidCard();
            data[i][1] = filtered.get(i).getName();
            data[i][2] = filtered.get(i).getNidn();
            data[i][3] = filtered.get(i).getExpertise();
            }
        String[] cols = {"ID", "Nama", "NIDN", "Expertise"};
        jTableLecture.setModel(new javax.swing.table.DefaultTableModel(data, cols));
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jMenuDashborMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuDashborMouseClicked
        // TODO add your handling code here:
        Dashbord dashbord = null;
        dashbord = new Dashbord();
        dashbord.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuDashborMouseClicked

    private void jMenuStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuStudentMouseClicked
        // TODO add your handling code here:
        StudentForm studentForm = null;
        try {
            studentForm = new StudentForm();
        } catch (SQLException ex) {
            System.getLogger(LectureForm.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        studentForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuStudentMouseClicked

    private void jMenuCourseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuCourseMouseClicked
        // TODO add your handling code here:
        CourseForm courseForm = null;
        courseForm = new CourseForm();
        courseForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuCourseMouseClicked

    private void jMenuKRSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuKRSMouseClicked
        // TODO add your handling code here:
        KRSForm krsForm = null;
        krsForm = new KRSForm();
        krsForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuKRSMouseClicked

    private void jButtonSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSearchMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSearchMouseClicked

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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new LectureForm().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(StudentForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuCourse;
    private javax.swing.JMenu jMenuDashbor;
    private javax.swing.JMenu jMenuKRS;
    private javax.swing.JMenu jMenuStudent;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableLecture;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextFieldExpert;
    private javax.swing.JTextField jTextFieldLecture;
    private javax.swing.JTextField jTextFieldNIDN;
    private javax.swing.JTextField jTextFieldNIK;
    private javax.swing.JTextField jTextFieldNama;
    // End of variables declaration//GEN-END:variables
}
