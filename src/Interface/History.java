/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import DatabaseConnect.Dbconnect;
import java.sql.Date;



/**
 *
 * @author hoang
 */
public class History extends javax.swing.JFrame {
     private Connection conn;
     private String username;
     private String fullName;

    /**
     * Creates new form History
     */
    public History() {
        initComponents();
        conn = new Dbconnect().getJDBConnection();
        loadBorrowingHistory();
    }
    
    public History(String username) {
        this.username = username;
        initComponents();
        conn = new Dbconnect().getJDBConnection();
        loadBorrowingHistory();
        loadFullName();
        
    }
    private void loadFullName() {
        fullName = username; // Giá trị mặc định
        if (conn == null || username == null || username.isEmpty()) return;

        try {
            String sql = "SELECT full_name FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tempFullName = rs.getString("full_name");
                // Nếu full_name không null và không rỗng, gán vào fullName, nếu không thì giữ nguyên username
                fullName = (tempFullName != null && !tempFullName.isEmpty()) ? tempFullName : username;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadBorrowingHistory() {
            try {
            // Kiểm tra username
            if (username == null || username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username không hợp lệ!");
                return;
            }

            // Kiểm tra kết nối
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu!");
                return;
            }

            // Load "Đang mượn" (borrowed or overdue)
            // Kết hợp bảng borrowings, books và categories
            String sql = "SELECT b.id AS 'Mã mượn', bk.title AS 'Tên sách', bk.author AS 'Tác giả', " +
                         "c.name AS 'Thể loại', " +
                         "b.borrow_date AS 'Ngày mượn', b.return_date AS 'Hạn trả', " +
                         "b.actual_return_date AS 'Ngày trả thực tế', b.status AS 'Trạng Thái' " +
                         "FROM borrowings b JOIN books bk ON b.book_id = bk.id " +
                         "LEFT JOIN categories c ON bk.category_id = c.id " +
                         "WHERE b.user_id = (SELECT id FROM users WHERE username = ?) AND b.status IN ('borrowed', 'overdue')";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            jTable1.setModel(resultSetToTableModel(rs));// Chuyển ResultSet thành TableModel và gán vào jTable1

            // Load "Đã trả" (returned)
            // Tương tự như trên nhưng chỉ lấy các bản ghi có status = 'returned'
            sql = "SELECT b.id AS 'Mã mượn', bk.title AS 'Tên sách', bk.author AS 'Tác giả', " +
                  "c.name AS 'Thể loại', " +
                  "b.borrow_date AS 'Ngày mượn', b.return_date AS 'Hạn trả', " +
                  "b.actual_return_date AS 'Ngày trả thực tế', b.status AS 'Trạng Thái' " +
                  "FROM borrowings b JOIN books bk ON b.book_id = bk.id " +
                  "LEFT JOIN categories c ON bk.category_id = c.id " +
                  "WHERE b.user_id = (SELECT id FROM users WHERE username = ?) AND b.status = 'returned'";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            jTable2.setModel(resultSetToTableModel(rs));// Chuyển ResultSet thành TableModel và gán vào jTable2

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải lịch sử: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
     // Phương thức này chuyển đổi ResultSet thành DefaultTableModel để hiển thị trên JTable
    private DefaultTableModel resultSetToTableModel(ResultSet rs) throws Exception {

    // Lấy thông tin metadata của ResultSet (số cột, tên cột, v.v.)
    ResultSetMetaData metaData = rs.getMetaData();
    // Lấy số lượng cột
    int columnCount = metaData.getColumnCount();
    // Tạo vector để lưu tên cột
    Vector<String> columnNames = new Vector<>();
    // Duyệt qua từng cột để lấy tên cột (label)
    for (int i = 1; i <= columnCount; i++) {
        columnNames.add(metaData.getColumnLabel(i));
    }

    // Tạo vector để lưu dữ liệu (các hàng)
    Vector<Vector<Object>> data = new Vector<>();
    // Duyệt qua từng hàng trong ResultSet
    while (rs.next()) {
        // Tạo vector để lưu dữ liệu của một hàng
        Vector<Object> row = new Vector<>();
        // Duyệt qua từng cột để lấy giá trị
        for (int i = 1; i <= columnCount; i++) {
            row.add(rs.getObject(i));
        }
        // Thêm hàng vào danh sách dữ liệu
        data.add(row);
    }
    // Trả về DefaultTableModel với dữ liệu và tên cột
    return new DefaultTableModel(data, columnNames);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/resources/images/reader-1713700-1453871.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Quản lý thư viện");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/resources/images/aaa.jpg"))); // NOI18N
        jButton1.setText("Trang chủ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/resources/images/bbb.png"))); // NOI18N
        jButton2.setText("Thư viện sách");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/resources/images/ddd.png"))); // NOI18N
        jButton3.setText("Quản lý thông tin");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/resources/images/chuong.png"))); // NOI18N
        jButton12.setText("Thông báo");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 570));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Lịch sử mượn sách");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã mượn", "Tên sách", "Tác giả	", "Ngày mượn", "Hạn trả	", "Ngày trả thực tế	", "Trạng thái "
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTabbedPane1.addTab("Đang mượn", jScrollPane1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã mượn", "Tên sách	", "Tác giả	", "Ngày mượn	", "Hạn trả	", "Ngày trả thực tế	", "Trạng thái "
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jTabbedPane1.addTab("Đã trả", jScrollPane2);

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setText("Trả sách ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel4)
                .addGap(50, 50, 50)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 550, 500));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/resources/images/Thiet-ke-khong-ten.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        User user = new User(this.username);
        user.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
           UserInformation us = new UserInformation(this.username);
           us.setVisible(true);
           this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Library lib = new Library(this.username);
        lib.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        TbUser tb = new TbUser(this.username);
        tb.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       try {
        // Lấy chỉ số hàng được chọn từ bảng jTable1 (bảng hiển thị lịch sử mượn sách)
        int selectedRow = jTable1.getSelectedRow();
        // Kiểm tra nếu không có hàng nào được chọn (trả về -1 nếu không chọn)
        if (selectedRow == -1) {
            // Hiển thị thông báo lỗi yêu cầu người dùng chọn một sách để trả
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách để trả!");
            // Thoát khỏi phương thức
            return;
        }

        // Lấy mã mượn (borrowingId) từ cột đầu tiên (cột 0) của hàng được chọn
        int borrowingId = (int) jTable1.getValueAt(selectedRow, 0);
        // Lấy tiêu đề sách từ cột thứ hai (cột 1) của hàng được chọn
        String bookTitle = (String) jTable1.getValueAt(selectedRow, 1);
        // Lấy ngày hiện tại để làm ngày trả thực tế
        java.util.Date utilDate = new java.util.Date(); // Ngày hiện tại (java.util.Date)
        Date sqlDate = new Date(utilDate.getTime());    // Chuyển thành java.sql.Date để dùng trong SQL

        // Kiểm tra trạng thái của bản ghi mượn trong bảng borrowings
        String checkStatusSql = "SELECT status FROM borrowings WHERE id = ?";
        PreparedStatement checkStatusStmt = conn.prepareStatement(checkStatusSql);
        checkStatusStmt.setInt(1, borrowingId);
        ResultSet statusRs = checkStatusStmt.executeQuery();
        if (statusRs.next()) {
            String status = statusRs.getString("status");
            if ("returned".equals(status)) {
                JOptionPane.showMessageDialog(this, "Sách này đã được trả trước đó!");
                return;
            }
        }

        // Sử dụng try-with-resources để tự động đóng tài nguyên
        try (PreparedStatement getReturnDateStmt = conn.prepareStatement("SELECT return_date FROM borrowings WHERE id = ?")) {
            getReturnDateStmt.setInt(1, borrowingId);
            try (ResultSet returnDateRs = getReturnDateStmt.executeQuery()) {
                Date returnDate = null;
                if (returnDateRs.next()) {
                    returnDate = returnDateRs.getDate("return_date");
                }
                if (returnDate == null) {
                    JOptionPane.showMessageDialog(this, "Không thể lấy ngày hạn trả!");
                    return;
                }

                boolean isOnTime = sqlDate.compareTo(returnDate) <= 0;
                String userNotification = isOnTime ? "Cảm ơn bạn đã trả sách đúng hạn!" : "Sách bạn mượn đã quá hạn trả!";
                String adminNotification = fullName + " đã trả sách: " + bookTitle;

                try (PreparedStatement pst = conn.prepareStatement(
                        "UPDATE borrowings SET status = ?, actual_return_date = ? WHERE id = ? AND user_id = (SELECT id FROM users WHERE username = ?)")) {
                    pst.setString(1, "returned");
                    pst.setDate(2, sqlDate);
                    pst.setInt(3, borrowingId);
                    pst.setString(4, username);
                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        try (PreparedStatement userStmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?")) {
                            userStmt.setString(1, username);
                            try (ResultSet userRs = userStmt.executeQuery()) {
                                int userId = -1;
                                if (userRs.next()) {
                                    userId = userRs.getInt("id");
                                } else {
                                    JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng với username: " + username);
                                    return;
                                }

                                try (PreparedStatement updateBookStmt = conn.prepareStatement(
                                        "UPDATE books SET quantity = quantity + 1 WHERE id = (SELECT book_id FROM borrowings WHERE id = ?)")) {
                                    updateBookStmt.setInt(1, borrowingId);
                                    updateBookStmt.executeUpdate();
                                }

                                // Gửi thông báo cho người dùng
                                try (PreparedStatement notifyUserStmt = conn.prepareStatement(
                                        "INSERT INTO notifications (user_id, message, created_at) VALUES (?, ?, NOW())")) {
                                    notifyUserStmt.setInt(1, userId);
                                    notifyUserStmt.setString(2, userNotification);
                                    notifyUserStmt.executeUpdate();
                                }

                                // Gửi thông báo cho admin
                                try (PreparedStatement notifyAdminStmt = conn.prepareStatement(
                                        "INSERT INTO notifications (user_id, message, created_at) VALUES (?, ?, NOW())")) {
                                    notifyAdminStmt.setInt(1, 1); // user_id của admin (hardcode = 1)
                                    notifyAdminStmt.setString(2, adminNotification);
                                    notifyAdminStmt.executeUpdate();
                                }

                                JOptionPane.showMessageDialog(this, "Trả sách thành công!");
                                loadBorrowingHistory();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể trả sách!");
                    }
                }
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi trả sách: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_jButton4ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(History.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new History().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
