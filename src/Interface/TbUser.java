/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import DatabaseConnect.Dbconnect;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hoang
 */
public class TbUser extends javax.swing.JFrame {

    private String username; // Thêm biến username nếu cần
    private Connection conn; // Chỉ khai báo một lần

    /**
     * Creates new form TbUser
     */
    public TbUser() {
        initComponents();
    }
    
     public TbUser(String username) {
        this.username = username; // Store the username
        initComponents();
        conn = new Dbconnect().getJDBConnection();
        loadNotifications();
    }
     
     
 private void loadNotifications() {
    try {
        // Lấy user_id từ bảng users dựa trên username
        String userSql = "SELECT id FROM users WHERE username = ?";
        // Tạo PreparedStatement để thực thi câu lệnh SQL an toàn
        PreparedStatement userStmt = conn.prepareStatement(userSql);
        // Gán giá trị username vào placeholder thứ 1
        userStmt.setString(1, username);
        // Thực thi câu lệnh truy vấn và lấy kết quả
        ResultSet userRs = userStmt.executeQuery();
        // Khởi tạo userId với giá trị mặc định -1
        int userId = -1;
        // Kiểm tra nếu có bản ghi khớp với username
        if (userRs.next()) {
            // Lấy giá trị id (userId) từ kết quả
            userId = userRs.getInt("id");
        } else {
            // Hiển thị thông báo lỗi nếu không tìm thấy người dùng
            JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng với username: " + username);
            // Thoát khỏi phương thức
            return;
        }
        // In userId ra console để debug
        System.out.println("User ID: " + userId);

        // Lấy thông tin trả sách từ bảng notifications và borrowings
        // Chỉ lấy các thông báo liên quan đến việc trả sách (message chứa '%đã trả sách%')
        // Sắp xếp theo thời gian giảm dần (DESC)
        String sql = "SELECT n.message, n.created_at, b.book_id, b.return_date, b.actual_return_date " +
                     "FROM notifications n " +
                     "JOIN borrowings b ON n.user_id = b.user_id AND n.message LIKE '%đã trả sách%' " +
                     "WHERE n.user_id = ? AND b.actual_return_date IS NOT NULL " +
                     "ORDER BY n.created_at DESC";
        // Tạo PreparedStatement để thực thi câu lệnh SQL
        PreparedStatement pst = conn.prepareStatement(sql);
        // Gán giá trị userId vào placeholder thứ 1
        pst.setInt(1, userId);
        // Thực thi câu lệnh truy vấn và lấy kết quả
        ResultSet rs = pst.executeQuery();

        // Lấy model của bảng jTable1 và ép kiểu thành DefaultTableModel
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        // Xóa tất cả các hàng hiện có trong bảng để chuẩn bị hiển thị dữ liệu mới
        model.setRowCount(0);
        // Biến đếm số lượng hàng (thông báo) được hiển thị
        int rowCount = 0;

        // Duyệt qua từng bản ghi trong ResultSet
        while (rs.next()) {
            // Tăng biến đếm số hàng
            rowCount++;
            // Lấy thông tin từ cột tương ứng
            String message = rs.getString("message"); // Lấy nội dung thông báo
            // Lấy thời gian tạo thông báo, chuyển thành chuỗi nếu không null
            String createdAt = rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toString() : "";
            // Lấy ngày hạn trả từ bảng borrowings
            java.sql.Date returnDate = rs.getDate("return_date");
            // Lấy ngày trả thực tế từ bảng borrowings
            java.sql.Date actualReturnDate = rs.getDate("actual_return_date");
            // Kiểm tra xem sách có được trả đúng hạn không
            boolean isOnTime = actualReturnDate != null && returnDate != null && !actualReturnDate.after(returnDate);
            // Tạo thông báo trạng thái dựa trên việc trả đúng hạn hay quá hạn
            String statusMessage = isOnTime ? "Cảm ơn bạn đã trả sách đúng hạn!" : "Sách bạn mượn đã quá hạn trả!";
            // Thêm một hàng mới vào model với thông tin trạng thái và thời gian
            model.addRow(new Object[]{statusMessage, createdAt});
        }
        // In số lượng thông báo được hiển thị ra console để debug
        System.out.println("Số thông báo hiển thị: " + rowCount);
        // Kiểm tra nếu không có thông báo nào được hiển thị
        if (rowCount == 0) {
            // Hiển thị thông báo cho người dùng nếu không có dữ liệu
            JOptionPane.showMessageDialog(this, "Không có thông báo nào để hiển thị!");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi tải thông báo: " + e.getMessage());
        e.printStackTrace();
    }
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
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/resources/images/cccc.png"))); // NOI18N
        jButton3.setText("Lịch sử mượn sách");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/resources/images/ddd.png"))); // NOI18N
        jButton4.setText("Quản lý thông tin");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel2)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 600));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel4.setText("Thông báo");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nội dung thông báo", "Thời gian "
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(199, 199, 199))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
                .addGap(72, 72, 72)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 500, 570));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/resources/images/Thiet-ke-khong-ten.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 748, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        User user = new User(this.username);
        user.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Library libarari = new Library(this.username);
        libarari.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        History his = new History(this.username);
        his.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
            UserInformation us = new UserInformation(this.username);
            us.setVisible(true);
            this.dispose();
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
            java.util.logging.Logger.getLogger(TbUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TbUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TbUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TbUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TbUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
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
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
