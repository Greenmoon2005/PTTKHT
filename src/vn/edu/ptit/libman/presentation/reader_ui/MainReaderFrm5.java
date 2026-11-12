package vn.edu.ptit.libman.presentation.reader_ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import vn.edu.ptit.libman.model.Reader5;
import vn.edu.ptit.libman.util.MockDatabase5;
import vn.edu.ptit.libman.util.SessionManager5;

/**
 * Màn hình chính Bạn đọc (MainReaderFrm5 / gdChinhBD5).
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 1 và UI Mockup (Hậu tố 5 - B23DCCN015).
 */
public class MainReaderFrm5 extends JFrame {
    private static final long serialVersionUID = 1L;

    private JButton btnSearchDoc;
    private JButton btnMyBorrows;
    private JButton btnProfile;
    private JButton btnLogout;
    private Reader5 currentUser;

    public MainReaderFrm5() {
        this(SessionManager5.getCurrentReader5() != null 
                ? SessionManager5.getCurrentReader5() 
                : MockDatabase5.getInstance().readers.get(0));
    }

    public MainReaderFrm5(Reader5 reader) {
        this.currentUser = reader;
        SessionManager5.setCurrentUser5(this.currentUser);
        initComponents();
    }

    private void initComponents() {
        setTitle("LIBMAN - CỔNG THÔNG TIN BẠN ĐỌC (MÃ SV: B23DCCN015 - HẬU TỐ 5)");
        setSize(900, 600);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel (Navy Theme)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(0x1B, 0x36, 0x5D));
        pnlHeader.setBorder(new EmptyBorder(18, 24, 18, 24));

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ THƯ VIỆN ĐẠI HỌC (LIBMAN)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);

        String readerName = currentUser != null ? currentUser.getFullName() : "Độc giả Khách";
        String readerCode = currentUser != null ? currentUser.getReaderCode() : "N/A";
        JLabel lblUser = new JLabel("Xin chào, " + readerName + " (" + readerCode + ")");
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

        // Button Search Doc (Module 1 Focus)
        btnSearchDoc = createMenuButton("🔍  TÌM KIẾM TÀI LIỆU (MODULE 1)", 
                "Tra cứu danh mục sách, giáo trình, tạp chí và kiểm tra tình trạng bản sao vật lý", 
                new Color(0x1B, 0x36, 0x5D), Color.WHITE);
        btnSearchDoc.addActionListener(this::btnSearchDocActionPerformed);

        // Button My Borrows
        btnMyBorrows = createMenuButton("📚  TÀI LIỆU ĐANG MƯỢN", 
                "Xem lịch sử mượn trả, hạn trả sách và thông tin thẻ bạn đọc", 
                Color.WHITE, new Color(0x1B, 0x36, 0x5D));
        btnMyBorrows.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Bạn đọc " + currentUser.getFullName() + " hiện đang mượn 1 cuốn sách:\n"
                + "• Bài giảng PTTKHT 2 (Bản sao: BC-PTTKHT-003) - Hạn trả: 15/11/2026", 
                "Thông tin tài liệu đang mượn", JOptionPane.INFORMATION_MESSAGE);
        });

        // Button Profile
        btnProfile = createMenuButton("👤  HỒ SƠ BẠN ĐỌC", 
                "Xem mã thẻ bạn đọc, lớp sinh viên, khoa viện và trạng thái tài khoản", 
                Color.WHITE, new Color(0x1B, 0x36, 0x5D));
        btnProfile.addActionListener(e -> {
            String cardBarcode = (currentUser != null && currentUser.getCard5() != null) 
                    ? currentUser.getCard5().getBarcode() : "CARD-015";
            JOptionPane.showMessageDialog(this, 
                "HỒ SƠ BẠN ĐỌC:\n"
                + "• Họ và tên: " + currentUser.getFullName() + "\n"
                + "• Mã sinh viên: " + currentUser.getReaderCode() + "\n"
                + "• Khoa: " + currentUser.getFaculty() + "\n"
                + "• Lớp: " + currentUser.getStudentClass() + "\n"
                + "• Mã vạch thẻ: " + cardBarcode + " (Dùng để quét mượn sách)\n"
                + "• Hạn mức mượn tối đa: " + currentUser.getMaxBorrowLimit() + " cuốn",
                "Thông tin hồ sơ cá nhân", JOptionPane.INFORMATION_MESSAGE);
        });

        // Button Logout
        btnLogout = createMenuButton("🚪  ĐĂNG XUẤT", 
                "Thoát phiên làm việc của bạn đọc trên hệ thống", 
                new Color(0xEF, 0x44, 0x44), Color.WHITE);
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        gbc.gridx = 0; gbc.gridy = 0;
        pnlCenter.add(btnSearchDoc, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        pnlCenter.add(btnMyBorrows, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlCenter.add(btnProfile, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        pnlCenter.add(btnLogout, gbc);

        add(pnlCenter, BorderLayout.CENTER);

        // Footer Panel
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFooter.setBackground(new Color(0xE2, 0xE8, 0xF0));
        pnlFooter.setBorder(new EmptyBorder(8, 0, 8, 0));
        JLabel lblFooter = new JLabel("Học viện Công nghệ Bưu chính Viễn thông - PTIT | Bài tập cá nhân PTTKHT - Đỗ Đức Tuấn Anh (B23DCCN015)");
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

    public void btnSearchDocActionPerformed(ActionEvent e) {
        // Mở giao diện Tìm kiếm Tài liệu (Module 1)
        SearchDocFrm5 searchFrm = new SearchDocFrm5(this);
        searchFrm.setVisible(true);
        this.setVisible(false);
    }

    public void btnLogoutActionPerformed(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận đăng xuất", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager5.clearSession();
            dispose();
        }
    }
}

