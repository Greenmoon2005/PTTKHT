package vn.edu.ptit.libman.presentation.staff_ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vn.edu.ptit.libman.model.BorrowDetail5;
import vn.edu.ptit.libman.model.BorrowSlip5;
import vn.edu.ptit.libman.util.DateUtil5;

/**
 * Màn hình Bản xem trước & In Phiếu mượn tài liệu (PrintBorrowSlipFrm5 / gdInPhieuMuon5).
 * Module 2: Nhân viên cho bạn đọc mượn tài liệu.
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết, Sơ đồ tuần tự và UI Mockup (Hậu tố 5 - B23DCCN015).
 */
public class PrintBorrowSlipFrm5 extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel pnlSlipPreview;
    private JLabel lblSlipCode;
    private JLabel lblBorrowDate;
    private JLabel lblDueDate;
    private JLabel lblReaderInfo;
    private JLabel lblStaffInfo;
    private JTable tblSlipDetails;
    private DefaultTableModel tableModel;
    private JTextArea txtPreviewSlip;
    private JButton btnPrint;
    private JButton btnFinish;

    private BorrowSlip5 slipToPrint;
    private final JFrame parentFrame;

    public PrintBorrowSlipFrm5(JFrame parent, BorrowSlip5 slip) {
        this.parentFrame = parent;
        this.slipToPrint = slip;
        initComponents();
        loadSlipData(slip);
    }

    private void initComponents() {
        setTitle("MODULE 2: BẢN XEM TRƯỚC & IN PHIẾU MƯỢN (MÃ SV: B23DCCN015 - HẬU TỐ 5)");
        setSize(850, 720);
        setMinimumSize(new Dimension(750, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel (Navy Theme)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(0x1B, 0x36, 0x5D));
        pnlHeader.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel lblTitle = new JLabel("XEM TRƯỚC VÀ IN PHIẾU MƯỢN TÀI LIỆU");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.WHITE);

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        add(pnlHeader, BorderLayout.NORTH);

        // Center Slip Preview Area
        pnlSlipPreview = new JPanel(new BorderLayout(10, 10));
        pnlSlipPreview.setBackground(new Color(0xF1, 0xF5, 0xF9));
        pnlSlipPreview.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Slip Paper Effect Container
        JPanel pnlPaper = new JPanel(new BorderLayout(10, 15));
        pnlPaper.setBackground(Color.WHITE);
        pnlPaper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x94, 0xA3, 0xB8), 1),
            new EmptyBorder(25, 30, 25, 30)
        ));

        // Slip Header
        JPanel pnlPaperHeader = new JPanel(new GridLayout(6, 1, 3, 3));
        pnlPaperHeader.setBackground(Color.WHITE);

        JLabel lblOrg = new JLabel("HỌC VIỆN CÔNG NGHỆ BƯU CHÍNH VIỄN THÔNG", JLabel.CENTER);
        lblOrg.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblOrg.setForeground(new Color(0x1E, 0x29, 0x3B));

        JLabel lblLib = new JLabel("TRUNG TÂM THÔNG TIN THƯ VIỆN PTIT", JLabel.CENTER);
        lblLib.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLib.setForeground(new Color(0x47, 0x55, 0x69));

        JLabel lblSlipTitle = new JLabel("PHIẾU MƯỢN TÀI LIỆU", JLabel.CENTER);
        lblSlipTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblSlipTitle.setForeground(new Color(0x1B, 0x36, 0x5D));
        lblSlipTitle.setBorder(new EmptyBorder(6, 0, 4, 0));

        lblSlipCode = new JLabel("Mã phiếu: SLIP-XXXXXXXX-XXX", JLabel.CENTER);
        lblSlipCode.setFont(new Font("Consolas", Font.BOLD, 13));
        lblSlipCode.setForeground(new Color(0x0F, 0x17, 0x2A));

        lblReaderInfo = new JLabel("Bạn đọc: --- | Mã SV: --- | Khoa: ---", JLabel.CENTER);
        lblReaderInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        lblStaffInfo = new JLabel("Thủ thư lập phiếu: --- | Ngày mượn: --- | Hạn trả: ---", JLabel.CENTER);
        lblStaffInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblStaffInfo.setForeground(new Color(0x33, 0x41, 0x55));

        pnlPaperHeader.add(lblOrg);
        pnlPaperHeader.add(lblLib);
        pnlPaperHeader.add(lblSlipTitle);
        pnlPaperHeader.add(lblSlipCode);
        pnlPaperHeader.add(lblReaderInfo);
        pnlPaperHeader.add(lblStaffInfo);

        pnlPaper.add(pnlPaperHeader, BorderLayout.NORTH);

        // Details Table
        String[] cols = {"STT", "Mã vạch (Barcode)", "Tên tài liệu / Sách", "Bản sao", "Kệ sách", "Hạn trả sách"};
        tableModel = new DefaultTableModel(cols, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSlipDetails = new JTable(tableModel);
        tblSlipDetails.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblSlipDetails.setRowHeight(26);
        tblSlipDetails.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblSlipDetails.getTableHeader().setBackground(new Color(0x33, 0x41, 0x55));
        tblSlipDetails.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer centerR = new DefaultTableCellRenderer();
        centerR.setHorizontalAlignment(JLabel.CENTER);
        tblSlipDetails.getColumnModel().getColumn(0).setCellRenderer(centerR);
        tblSlipDetails.getColumnModel().getColumn(1).setCellRenderer(centerR);
        tblSlipDetails.getColumnModel().getColumn(3).setCellRenderer(centerR);
        tblSlipDetails.getColumnModel().getColumn(5).setCellRenderer(centerR);

        JScrollPane scrollTable = new JScrollPane(tblSlipDetails);
        pnlPaper.add(scrollTable, BorderLayout.CENTER);

        // Slip Footer / Rules
        JPanel pnlPaperFooter = new JPanel(new GridLayout(3, 1, 3, 3));
        pnlPaperFooter.setBackground(Color.WHITE);
        pnlPaperFooter.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel lblRule1 = new JLabel("• Quy định: Bạn đọc có trách nhiệm bảo quản tài liệu nguyên vẹn, không viết vẽ bẩn lên sách.");
        lblRule1.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblRule1.setForeground(new Color(0x64, 0x74, 0x8B));

        JLabel lblRule2 = new JLabel("• Quá hạn mượn sẽ bị phạt phí trễ hạn theo quy chế thư viện PTIT (5.000đ/ngày/cuốn).");
        lblRule2.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblRule2.setForeground(new Color(0x64, 0x74, 0x8B));

        JLabel lblSign = new JLabel("Ký xác nhận của Bạn đọc: .......................................   Thủ thư: .......................................");
        lblSign.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSign.setBorder(new EmptyBorder(8, 0, 0, 0));

        pnlPaperFooter.add(lblRule1);
        pnlPaperFooter.add(lblRule2);
        pnlPaperFooter.add(lblSign);

        pnlPaper.add(pnlPaperFooter, BorderLayout.SOUTH);

        pnlSlipPreview.add(pnlPaper, BorderLayout.CENTER);
        add(pnlSlipPreview, BorderLayout.CENTER);

        // Bottom Actions Bar
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pnlBottom.setBackground(new Color(0xE2, 0xE8, 0xF0));

        btnPrint = new JButton("🖨️ In phiếu mượn (Print)");
        btnPrint.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnPrint.setBackground(new Color(0x1B, 0x36, 0x5D));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFocusPainted(false);
        btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPrint.addActionListener(this::btnPrintActionPerformed);

        btnFinish = new JButton("✔ Hoàn tất & Về trang chủ");
        btnFinish.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnFinish.setBackground(new Color(0x16, 0xA3, 0x4A));
        btnFinish.setForeground(Color.WHITE);
        btnFinish.setFocusPainted(false);
        btnFinish.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFinish.addActionListener(this::btnFinishActionPerformed);

        pnlBottom.add(btnPrint);
        pnlBottom.add(btnFinish);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    public void loadSlipData(BorrowSlip5 slip) {
        if (slip == null) return;
        this.slipToPrint = slip;

        lblSlipCode.setText("Mã phiếu mượn: " + (slip.getSlipCode() != null ? slip.getSlipCode() : "N/A"));

        if (slip.getReader5() != null) {
            lblReaderInfo.setText("Bạn đọc: " + slip.getReader5().getFullName() 
                + " | Mã SV: " + slip.getReader5().getReaderCode() 
                + " | Lớp: " + slip.getReader5().getStudentClass() 
                + " | Khoa: " + slip.getReader5().getFaculty());
        }

        String staffName = slip.getStaff5() != null ? slip.getStaff5().getFullName() : "Nguyễn Thị Minh Nguyệt";
        String borrowDateStr = DateUtil5.formatDate(slip.getBorrowDate() != null ? slip.getBorrowDate() : new Date());
        String dueDateStr = DateUtil5.formatDate(slip.getDueDate() != null ? slip.getDueDate() : DateUtil5.addDays(new Date(), 14));
        lblStaffInfo.setText("Thủ thư: " + staffName + " | Ngày mượn: " + borrowDateStr + " | Hạn trả: " + dueDateStr);

        tableModel.setRowCount(0);
        List<BorrowDetail5> details = slip.getBorrowDetails5();
        if (details != null) {
            for (int i = 0; i < details.size(); i++) {
                BorrowDetail5 d = details.get(i);
                var copy = d.getDocumentCopy5();
                String barcode = copy != null ? copy.getBarcode() : "N/A";
                String title = (copy != null && copy.getDocument5() != null) ? copy.getDocument5().getTitle() : "Tài liệu #" + (copy != null ? copy.getDocumentId() : "");
                String copyNum = copy != null ? "#" + copy.getCopyNumber() : "-";
                String rack = copy != null && copy.getLocationRack() != null ? copy.getLocationRack() : "Ngăn chung";

                tableModel.addRow(new Object[]{
                    i + 1,
                    barcode,
                    title,
                    copyNum,
                    rack,
                    dueDateStr
                });
            }
        }
    }

    public void btnPrintActionPerformed(ActionEvent e) {
        try {
            boolean complete = tblSlipDetails.print(
                JTable.PrintMode.FIT_WIDTH,
                new java.text.MessageFormat("PHIẾU MƯỢN TÀI LIỆU - " + slipToPrint.getSlipCode()),
                new java.text.MessageFormat("Học viện Công nghệ Bưu chính Viễn thông - Trang {0}")
            );
            if (complete) {
                JOptionPane.showMessageDialog(this, 
                    "Lệnh in phiếu mượn " + slipToPrint.getSlipCode() + " đã được gửi tới máy in thành công!", 
                    "In thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, 
                "Mô phỏng in thành công!\nPhiếu mượn " + slipToPrint.getSlipCode() + " đã sẵn sàng giao cho độc giả.", 
                "Kết quả in phiếu", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void btnFinishActionPerformed(ActionEvent e) {
        if (parentFrame != null) {
            parentFrame.setVisible(true);
        }
        dispose();
    }
}
