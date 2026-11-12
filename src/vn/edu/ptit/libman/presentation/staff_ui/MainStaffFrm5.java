package vn.edu.ptit.libman.presentation.staff_ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import vn.edu.ptit.libman.model.Staff5;
import vn.edu.ptit.libman.util.MockDatabase5;
import vn.edu.ptit.libman.util.SessionManager5;

/**
 * Màn hình chính Bàn làm việc Thủ thư (MainStaffFrm5 / gdChinhNV5).
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 2 và UI Mockup (Hậu tố 5 - B23DCCN015).
 */
public class MainStaffFrm5 extends JFrame {
    private static final long serialVersionUID = 1L;

    private JButton btnBorrowDoc;
    private JButton btnReturnDoc;
    private JButton btnManageDoc;
    private JButton btnLogout;
    private Staff5 currentStaff;

    public MainStaffFrm5() {
        this(SessionManager5.getCurrentStaff5() != null 
                ? SessionManager5.getCurrentStaff5() 
                : MockDatabase5.getInstance().staffs.get(0));
    }

    public MainStaffFrm5(Staff5 staff) {
        this.currentStaff = staff;
        SessionManager5.setCurrentUser5(this.currentStaff);
        initComponents();
    }

    private void initComponents() {
        setTitle("LIBMAN - BÀN LÀM VIỆC THỦ THƯ TÁC NGHIỆP (MÃ SV: B23DCCN015 - HẬU TỐ 5)");
        setSize(920, 600);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header (Navy Theme)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(0x1B, 0x36, 0x5D));
        pnlHeader.setBorder(new EmptyBorder(18, 24, 18, 24));

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ THƯ VIỆN ĐẠI HỌC (LIBMAN)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);

        String staffName = currentStaff != null ? currentStaff.getFullName() : "Thủ thư trực quầy";
        String staffCode = currentStaff != null ? currentStaff.getStaffCode() : "N/A";
        JLabel lblUser = new JLabel("Thủ thư: " + staffName + " (" + staffCode + ")");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUser.setForeground(new Color(0x93, 0xC5, 0xFD));

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(lblUser, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Center Panel (Dashboard Buttons)
        JPanel pnlCenter = new JPanel(new GridBagLayout());
        pnlCenter.setBackground(new Color(0xF8, 0xFA, 0xFC));
        pnlCenter.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Button Cho mượn tài liệu (MODULE 2 FOCUS)
        btnBorrowDoc = createMenuButton("📖  CHO MƯỢN TÀI LIỆU (MODULE 2)", 
                "Quét thẻ độc giả, quét mã vạch các cuốn sách theo vòng lặp, kiểm tra hạn mức và lập phiếu mượn", 
                new Color(0x1B, 0x36, 0x5D), Color.WHITE);
        btnBorrowDoc.addActionListener(this::btnBorrowDocActionPerformed);

        // Button Nhận trả tài liệu
        btnReturnDoc = createMenuButton("🔄  NHẬN TRẢ TÀI LIỆU", 
                "Quét mã tài liệu trả, kiểm tra trễ hạn hoặc hỏng hóc, tính phí phạt và cập nhật kho", 
                Color.WHITE, new Color(0x1B, 0x36, 0x5D));
        btnReturnDoc.addActionListener(this::btnReturnDocActionPerformed);

        // Button Quản lý kho sách
        btnManageDoc = createMenuButton("📦  QUẢN LÝ TÀI LIỆU & BẢN SAO", 
                "Thêm mới tựa sách, in mã vạch bản sao, xếp vị trí ngăn kệ và cập nhật tình trạng", 
                Color.WHITE, new Color(0x1B, 0x36, 0x5D));
        btnManageDoc.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Chức năng Quản lý Danh mục tài liệu và Nhà cung cấp thuộc phân hệ Mở rộng.", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });

        // Button Logout
        btnLogout = createMenuButton("🚪  ĐĂNG XUẤT", 
                "Thoát phiên làm việc của thủ thư", 
                new Color(0xEF, 0x44, 0x44), Color.WHITE);
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        gbc.gridx = 0; gbc.gridy = 0;
        pnlCenter.add(btnBorrowDoc, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        pnlCenter.add(btnReturnDoc, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlCenter.add(btnManageDoc, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        pnlCenter.add(btnLogout, gbc);

        add(pnlCenter, BorderLayout.CENTER);

        // Footer
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFooter.setBackground(new Color(0xE2, 0xE8, 0xF0));
        pnlFooter.setBorder(new EmptyBorder(8, 0, 8, 0));
        JLabel lblFooter = new JLabel("Học viện Công nghệ Bưu chính Viễn thông - PTIT | Quầy Dịch vụ Lưu thông Độc giả (B23DCCN015)");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(0x47, 0x55, 0x69));
        pnlFooter.add(lblFooter);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String title, String desc, Color bg, Color fg) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCB, 0xD5, 0xE1), 1),
            new EmptyBorder(16, 20, 16, 20)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblBtnTitle = new JLabel(title);
        lblBtnTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblBtnTitle.setForeground(fg);

        JLabel lblBtnDesc = new JLabel("<html><body style='width: 250px;'>" + desc + "</body></html>");
        lblBtnDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblBtnDesc.setForeground(fg == Color.WHITE ? new Color(0xEB, 0xF8, 0xFF) : new Color(0x64, 0x74, 0x8B));
        lblBtnDesc.setBorder(new EmptyBorder(8, 0, 0, 0));

        btn.add(lblBtnTitle, BorderLayout.NORTH);
        btn.add(lblBtnDesc, BorderLayout.CENTER);
        return btn;
    }

    public void btnBorrowDocActionPerformed(ActionEvent e) {
        // Mở giao diện Lập phiếu cho mượn tài liệu (Module 2)
        BorrowDocFrm5 borrowFrm = new BorrowDocFrm5(this, currentStaff);
        borrowFrm.setVisible(true);
        this.setVisible(false);
    }

    public void btnReturnDocActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, 
            "Giao diện Nhận trả tài liệu từ bạn đọc.\n"
            + "Chức năng nằm ngoài phạm vi 2 module cá nhân (Module 1 & 2).", 
            "Thông tin chức năng", JOptionPane.INFORMATION_MESSAGE);
    }

    public void btnLogoutActionPerformed(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn đăng xuất khỏi bàn làm việc thủ thư?", 
                "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager5.clearSession();
            dispose();
        }
    }
}

