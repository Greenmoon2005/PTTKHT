package vn.edu.ptit.libman;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import vn.edu.ptit.libman.presentation.reader_ui.MainReaderFrm5;
import vn.edu.ptit.libman.presentation.staff_ui.MainStaffFrm5;

/**
 * Điểm khởi chạy chính toàn hệ thống LibMan (Hậu tố 5 - B23DCCN015).
 * Cung cấp cổng chọn giữa Module 1 (Bạn đọc tìm tài liệu) và Module 2 (Thủ thư cho mượn tài liệu).
 */
public class Main5 extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        // Cấu hình giao diện Look & Feel hệ thống
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            Main5 launcher = new Main5();
            launcher.setVisible(true);
        });
    }

    public Main5() {
        initComponents();
    }

    private void initComponents() {
        setTitle("LIBMAN - HỆ THỐNG QUẢN LÝ THƯ VIỆN ĐẠI HỌC (PTIT - B23DCCN015)");
        setSize(780, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel (Navy Theme)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(0x1B, 0x36, 0x5D));
        pnlHeader.setBorder(new EmptyBorder(22, 25, 22, 25));

        JLabel lblTitle = new JLabel("HỌC VIỆN CÔNG NGHỆ BƯU CHÍNH VIỄN THÔNG (PTIT)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSubtitle = new JLabel("BÀI TẬP CÁ NHÂN PTTKHT - 2 MODULES THỰC THI (MÃ SV: B23DCCN015 - HẬU TỐ 5)");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(0xBA, 0xE6, 0xFD));
        lblSubtitle.setBorder(new EmptyBorder(5, 0, 0, 0));

        JPanel pnlTitle = new JPanel(new GridLayout(2, 1));
        pnlTitle.setOpaque(false);
        pnlTitle.add(lblTitle);
        pnlTitle.add(lblSubtitle);
        pnlHeader.add(pnlTitle, BorderLayout.WEST);

        add(pnlHeader, BorderLayout.NORTH);

        // Center Panel (Two Module Cards)
        JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 25, 25));
        pnlCenter.setBackground(new Color(0xF8, 0xFA, 0xFC));
        pnlCenter.setBorder(new EmptyBorder(35, 40, 35, 40));

        // Card 1: Module 1
        JPanel cardMod1 = createModuleCard(
            "MODULE 1: BẠN ĐỌC TÌM THÔNG TIN TÀI LIỆU",
            "Dành cho Bạn đọc / Sinh viên:\n\n"
            + "• Tra cứu kho tài liệu theo từ khóa và thể loại\n"
            + "• Xem chi tiết tác giả, nhà xuất bản, năm XB\n"
            + "• Kiểm tra tình trạng bản sao vật lý tại các ngăn kệ (Có sẵn / Đang mượn)\n"
            + "• So khớp kịch bản Use Case 1 & Sơ đồ lớp chi tiết 1",
            "🚀 Khởi chạy Module 1 (Bạn đọc)",
            new Color(0x1B, 0x36, 0x5D),
            e -> {
                MainReaderFrm5 readerFrm = new MainReaderFrm5();
                readerFrm.setVisible(true);
            }
        );

        // Card 2: Module 2
        JPanel cardMod2 = createModuleCard(
            "MODULE 2: THỦ THƯ CHO BẠN ĐỌC MƯỢN TÀI LIỆU",
            "Dành cho Nhân viên Thư viện tại quầy:\n\n"
            + "• Quét mã thẻ bạn đọc & kiểm tra trạng thái hiệu lực\n"
            + "• Xác thực số sách đang mượn (< 5 cuốn)\n"
            + "• Vòng lặp quét mã vạch từng cuốn sách\n"
            + "• Lưu phiếu mượn qua Transaction CSDL chuẩn 3NF\n"
            + "• Xem trước và in phiếu mượn giao cho bạn đọc",
            "🚀 Khởi chạy Module 2 (Thủ thư)",
            new Color(0x04, 0x78, 0x57),
            e -> {
                MainStaffFrm5 staffFrm = new MainStaffFrm5();
                staffFrm.setVisible(true);
            }
        );

        pnlCenter.add(cardMod1);
        pnlCenter.add(cardMod2);
        add(pnlCenter, BorderLayout.CENTER);

        // Footer Panel
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFooter.setBackground(new Color(0xE2, 0xE8, 0xF0));
        pnlFooter.setBorder(new EmptyBorder(10, 0, 10, 0));
        JLabel lblInfo = new JLabel("Sinh viên: Đỗ Đức Tuấn Anh | Mã SV: B23DCCN015 | Hệ thống LibMan | Đề 01");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInfo.setForeground(new Color(0x47, 0x55, 0x69));
        pnlFooter.add(lblInfo);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    private JPanel createModuleCard(String title, String desc, String btnText, Color themeColor, java.awt.event.ActionListener action) {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCB, 0xD5, 0xE1), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lblCardTitle = new JLabel("<html><body style='width: 250px; font-weight: bold;'>" + title + "</body></html>");
        lblCardTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCardTitle.setForeground(themeColor);

        JTextArea txtDesc = new JTextArea(desc);
        txtDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDesc.setForeground(new Color(0x33, 0x41, 0x55));
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setBackground(Color.WHITE);

        JButton btnLaunch = new JButton(btnText);
        btnLaunch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLaunch.setBackground(themeColor);
        btnLaunch.setForeground(Color.WHITE);
        btnLaunch.setFocusPainted(false);
        btnLaunch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLaunch.setPreferredSize(new Dimension(200, 40));
        btnLaunch.addActionListener(action);

        panel.add(lblCardTitle, BorderLayout.NORTH);
        panel.add(txtDesc, BorderLayout.CENTER);
        panel.add(btnLaunch, BorderLayout.SOUTH);
        return panel;
    }
}

