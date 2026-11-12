package vn.edu.ptit.libman.presentation.staff_ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vn.edu.ptit.libman.dao.BorrowSlipDAO5;
import vn.edu.ptit.libman.dao.DocumentCopyDAO5;
import vn.edu.ptit.libman.dao.ReaderDAO5;
import vn.edu.ptit.libman.model.*;
import vn.edu.ptit.libman.util.DateUtil5;
import vn.edu.ptit.libman.util.MockDatabase5;

/**
 * Màn hình Lập phiếu cho mượn tài liệu (BorrowDocFrm5 / gdChoMuonTL5).
 * Module 2: Nhân viên cho bạn đọc mượn tài liệu.
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết, Sơ đồ tuần tự và Kịch bản Use Case (Hậu tố 5 - B23DCCN015).
 */
public class BorrowDocFrm5 extends JFrame {
    private static final long serialVersionUID = 1L;

    // Component Section 1: Thẻ bạn đọc
    private JTextField txtCardCode;
    private JButton btnCheckCard;
    private JLabel lblReaderName;
    private JLabel lblReaderCode;
    private JLabel lblReaderStatus;
    private JLabel lblCurrentBorrowCount;

    // Component Section 2: Quét mã vạch tài liệu mượn (Loop)
    private JTextField txtCopyBarcode;
    private JButton btnAddCopy;
    private JTable tblBorrowItems;
    private DefaultTableModel tableModel;
    private JButton btnRemoveItem;

    // Component Section 3: Xác nhận & Điều khiển
    private JButton btnConfirmLoan;
    private JButton btnCancel;
    private JLabel lblSummaryTotal;

    // Data Models
    private Reader5 currentReader;
    private Staff5 currentStaff;
    private BorrowSlip5 currentBorrowSlip;
    private List<DocumentCopy5> listSelectedCopies;
    private int currentBorrowedCount = 0;

    // DAOs
    private final ReaderDAO5 readerDAO;
    private final DocumentCopyDAO5 copyDAO;
    private final BorrowSlipDAO5 borrowSlipDAO;
    private final JFrame parentFrame;

    public BorrowDocFrm5(JFrame parent, Staff5 staff) {
        this.parentFrame = parent;
        this.currentStaff = staff != null ? staff : MockDatabase5.getInstance().staffs.get(0);
        this.readerDAO = new ReaderDAO5();
        this.copyDAO = new DocumentCopyDAO5();
        this.borrowSlipDAO = new BorrowSlipDAO5();
        this.listSelectedCopies = new ArrayList<>();
        this.currentBorrowSlip = new BorrowSlip5();

        initComponents();
        setCopyInputEnabled(false);
    }

    private void initComponents() {
        setTitle("MODULE 2: LẬP PHIẾU CHO MƯỢN TÀI LIỆU (MÃ SV: B23DCCN015 - HẬU TỐ 5)");
        setSize(1020, 720);
        setMinimumSize(new Dimension(880, 580));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Header (Navy Theme)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(0x1B, 0x36, 0x5D));
        pnlHeader.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel lblTitle = new JLabel("LẬP PHIẾU CHO MƯỢN TÀI LIỆU TẠI QUẦY");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.WHITE);

        String staffText = currentStaff != null ? "Thủ thư: " + currentStaff.getFullName() : "";
        JLabel lblStaff = new JLabel(staffText);
        lblStaff.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblStaff.setForeground(new Color(0xBA, 0xE6, 0xFD));

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(lblStaff, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // 2. Main Content
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setBackground(new Color(0xF8, 0xFA, 0xFC));
        pnlMain.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Box 1: Reader Card Scan
        JPanel pnlReader = new JPanel(new GridBagLayout());
        pnlReader.setBackground(Color.WHITE);
        pnlReader.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xCB, 0xD5, 0xE1), 1),
                " 1. Xác thực Bạn đọc & Kiểm tra Thẻ thư viện (tblCard5 / tblReader5) ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), new Color(0x1B, 0x36, 0x5D)
            ),
            new EmptyBorder(10, 15, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblScanCard = new JLabel("Quét mã thẻ / Nhập MSV:");
        lblScanCard.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlReader.add(lblScanCard, gbc);

        txtCardCode = new JTextField(20);
        txtCardCode.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtCardCode.setToolTipText("Quét mã vạch hoặc nhập mã: CARD-015, B23DCCN015...");
        txtCardCode.addActionListener(this::btnCheckCardActionPerformed);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        pnlReader.add(txtCardCode, gbc);

        btnCheckCard = new JButton("✔ Xác thực thẻ");
        btnCheckCard.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCheckCard.setBackground(new Color(0x1B, 0x36, 0x5D));
        btnCheckCard.setForeground(Color.WHITE);
        btnCheckCard.setFocusPainted(false);
        btnCheckCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCheckCard.addActionListener(this::btnCheckCardActionPerformed);
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0;
        pnlReader.add(btnCheckCard, gbc);

        // Reader Info Display Row
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        pnlReader.add(new JLabel("Họ tên bạn đọc:"), gbc);
        lblReaderName = new JLabel("--- Chưa xác thực ---");
        lblReaderName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblReaderName.setForeground(new Color(0x1B, 0x36, 0x5D));
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        pnlReader.add(lblReaderName, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0;
        lblReaderCode = new JLabel("Mã SV: ---");
        lblReaderCode.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pnlReader.add(lblReaderCode, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        pnlReader.add(new JLabel("Tình trạng thẻ:"), gbc);
        lblReaderStatus = new JLabel("Chưa nạp");
        lblReaderStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        pnlReader.add(lblReaderStatus, gbc);

        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0;
        lblCurrentBorrowCount = new JLabel("Số sách đang giữ: 0 / 5");
        lblCurrentBorrowCount.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCurrentBorrowCount.setForeground(new Color(0x47, 0x55, 0x69));
        pnlReader.add(lblCurrentBorrowCount, gbc);

        pnlMain.add(pnlReader);
        pnlMain.add(Box.createVerticalStrut(12));

        // Box 2: Copy Barcode Scan & Table (Loop)
        JPanel pnlScanCopy = new JPanel(new BorderLayout(8, 8));
        pnlScanCopy.setBackground(Color.WHITE);
        pnlScanCopy.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xCB, 0xD5, 0xE1), 1),
                " 2. Vòng lặp Quét mã vạch Bản sao tài liệu mượn (Loop - tblDocumentCopy5) ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), new Color(0x1B, 0x36, 0x5D)
            ),
            new EmptyBorder(10, 15, 10, 15)
        ));

        // Scan Input Row
        JPanel pnlCopyInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlCopyInput.setOpaque(false);
        JLabel lblScanCopy = new JLabel("Quét mã vạch bản sao (Barcode):");
        lblScanCopy.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtCopyBarcode = new JTextField(18);
        txtCopyBarcode.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtCopyBarcode.setToolTipText("Quét mã vạch: BC-PTTKHT-001, BC-JAVA-001, BC-CC-001...");
        txtCopyBarcode.addActionListener(this::btnAddCopyActionPerformed);

        btnAddCopy = new JButton("➕ Thêm vào phiếu");
        btnAddCopy.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAddCopy.setBackground(new Color(0x16, 0xA3, 0x4A));
        btnAddCopy.setForeground(Color.WHITE);
        btnAddCopy.setFocusPainted(false);
        btnAddCopy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddCopy.addActionListener(this::btnAddCopyActionPerformed);

        btnRemoveItem = new JButton("❌ Xóa dòng chọn");
        btnRemoveItem.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRemoveItem.setBackground(new Color(0xEF, 0x44, 0x44));
        btnRemoveItem.setForeground(Color.WHITE);
        btnRemoveItem.setFocusPainted(false);
        btnRemoveItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRemoveItem.addActionListener(this::btnRemoveItemActionPerformed);

        pnlCopyInput.add(lblScanCopy);
        pnlCopyInput.add(txtCopyBarcode);
        pnlCopyInput.add(btnAddCopy);
        pnlCopyInput.add(btnRemoveItem);

        pnlScanCopy.add(pnlCopyInput, BorderLayout.NORTH);

        // Borrow Items Table
        String[] columns = {"STT", "Mã vạch (Barcode)", "Tên tài liệu / Tựa sách", "Bản sao #", "Vị trí kệ/ngăn", "Tình trạng sách", "Hạn trả"};
        tableModel = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblBorrowItems = new JTable(tableModel);
        tblBorrowItems.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblBorrowItems.setRowHeight(26);
        tblBorrowItems.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblBorrowItems.getTableHeader().setBackground(new Color(0x1B, 0x36, 0x5D));
        tblBorrowItems.getTableHeader().setForeground(Color.WHITE);

        DefaultTableCellRenderer centerR = new DefaultTableCellRenderer();
        centerR.setHorizontalAlignment(JLabel.CENTER);
        tblBorrowItems.getColumnModel().getColumn(0).setCellRenderer(centerR);
        tblBorrowItems.getColumnModel().getColumn(1).setCellRenderer(centerR);
        tblBorrowItems.getColumnModel().getColumn(3).setCellRenderer(centerR);
        tblBorrowItems.getColumnModel().getColumn(6).setCellRenderer(centerR);

        tblBorrowItems.getColumnModel().getColumn(0).setPreferredWidth(45);
        tblBorrowItems.getColumnModel().getColumn(1).setPreferredWidth(140);
        tblBorrowItems.getColumnModel().getColumn(2).setPreferredWidth(320);
        tblBorrowItems.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblBorrowItems.getColumnModel().getColumn(4).setPreferredWidth(140);
        tblBorrowItems.getColumnModel().getColumn(5).setPreferredWidth(110);
        tblBorrowItems.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane scrollTable = new JScrollPane(tblBorrowItems);
        scrollTable.setPreferredSize(new Dimension(800, 200));
        pnlScanCopy.add(scrollTable, BorderLayout.CENTER);

        // Summary Bar in Box 2
        JPanel pnlSummary = new JPanel(new BorderLayout());
        pnlSummary.setOpaque(false);
        pnlSummary.setBorder(new EmptyBorder(6, 0, 0, 0));
        lblSummaryTotal = new JLabel("Tổng số tài liệu mượn trong phiếu: 0 cuốn (Hạn trả mặc định: 14 ngày)");
        lblSummaryTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSummaryTotal.setForeground(new Color(0x1B, 0x36, 0x5D));
        pnlSummary.add(lblSummaryTotal, BorderLayout.WEST);
        pnlScanCopy.add(pnlSummary, BorderLayout.SOUTH);

        pnlMain.add(pnlScanCopy);
        pnlMain.add(Box.createVerticalStrut(15));

        // Box 3: Bottom Action Controls
        JPanel pnlControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlControls.setOpaque(false);

        btnCancel = new JButton("Hủy bỏ & Quay lại");
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCancel.setBackground(new Color(0xE2, 0xE8, 0xF0));
        btnCancel.setForeground(new Color(0x1E, 0x29, 0x3B));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.setVisible(true);
            }
            dispose();
        });

        btnConfirmLoan = new JButton("💾 Xác nhận Cho mượn & In Phiếu (Submit)");
        btnConfirmLoan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirmLoan.setBackground(new Color(0x1B, 0x36, 0x5D));
        btnConfirmLoan.setForeground(Color.WHITE);
        btnConfirmLoan.setFocusPainted(false);
        btnConfirmLoan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmLoan.setEnabled(false);
        btnConfirmLoan.addActionListener(this::btnConfirmLoanActionPerformed);

        pnlControls.add(btnCancel);
        pnlControls.add(btnConfirmLoan);

        pnlMain.add(pnlControls);

        add(pnlMain, BorderLayout.CENTER);
    }

    private void setCopyInputEnabled(boolean enabled) {
        txtCopyBarcode.setEnabled(enabled);
        btnAddCopy.setEnabled(enabled);
        btnRemoveItem.setEnabled(enabled);
    }

    /**
     * Bước 1: Quét thẻ độc giả và xác thực điều kiện mượn sách.
     */
    public void btnCheckCardActionPerformed(ActionEvent e) {
        String cardCode = txtCardCode.getText().trim();
        if (cardCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng quét hoặc nhập mã thẻ bạn đọc!", 
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        currentReader = readerDAO.getReaderByCardBarcode5(cardCode);

        if (currentReader == null) {
            lblReaderName.setText("Không tìm thấy bạn đọc");
            lblReaderName.setForeground(new Color(0xDC, 0x26, 0x26));
            lblReaderStatus.setText("Thẻ không hợp lệ");
            lblReaderStatus.setForeground(new Color(0xDC, 0x26, 0x26));
            setCopyInputEnabled(false);
            btnConfirmLoan.setEnabled(false);
            JOptionPane.showMessageDialog(this, 
                "Không tìm thấy thông tin bạn đọc khớp với mã: '" + cardCode + "'!\n"
                + "Vui lòng kiểm tra lại mã vạch thẻ hoặc mã sinh viên.", 
                "Lỗi xác thực độc giả", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra hiệu lực thẻ
        Card5 card = currentReader.getCard5();
        if (card != null && !card.isValid()) {
            lblReaderName.setText(currentReader.getFullName());
            lblReaderStatus.setText("Thẻ đã hết hạn / Bị khóa");
            lblReaderStatus.setForeground(new Color(0xDC, 0x26, 0x26));
            setCopyInputEnabled(false);
            btnConfirmLoan.setEnabled(false);
            JOptionPane.showMessageDialog(this, 
                "Thẻ thư viện của bạn đọc " + currentReader.getFullName() + " hiện đã hết hạn hoặc bị khóa!\n"
                + "Vui lòng liên hệ quầy hành chính để gia hạn thẻ.", 
                "Thẻ không khả dụng", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra số sách đang mượn
        currentBorrowedCount = readerDAO.getCurrentBorrowedCount5(currentReader.getId());
        lblReaderName.setText(currentReader.getFullName());
        lblReaderName.setForeground(new Color(0x1B, 0x36, 0x5D));
        lblReaderCode.setText("Mã SV: " + currentReader.getReaderCode() + " | Lớp: " + currentReader.getStudentClass());
        lblReaderStatus.setText("Hợp lệ (Đang hoạt động)");
        lblReaderStatus.setForeground(new Color(0x16, 0xA3, 0x4A));
        lblCurrentBorrowCount.setText("Số sách đang giữ: " + currentBorrowedCount + " / " + currentReader.getMaxBorrowLimit() + " cuốn");

        if (currentBorrowedCount >= currentReader.getMaxBorrowLimit()) {
            setCopyInputEnabled(false);
            btnConfirmLoan.setEnabled(false);
            JOptionPane.showMessageDialog(this, 
                "Bạn đọc " + currentReader.getFullName() + " đã mượn tối đa 5 cuốn sách chưa trả!\n"
                + "Không thể mượn thêm tài liệu nào trước khi trả bớt sách cũ.", 
                "Đã đạt giới hạn mượn", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Độc giả hợp lệ -> kích hoạt vùng quét sách
        setCopyInputEnabled(true);
        txtCopyBarcode.requestFocus();
        refreshTable();
    }

    /**
     * Bước 2: Vòng lặp quét mã vạch từng cuốn sách và bổ sung vào bảng.
     */
    public void btnAddCopyActionPerformed(ActionEvent e) {
        if (currentReader == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng xác thực thẻ bạn đọc trước!", 
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String barcode = txtCopyBarcode.getText().trim();
        if (barcode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng quét hoặc nhập mã vạch cuốn sách!", 
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra đã có trong danh sách đang chọn chưa
        for (DocumentCopy5 c : listSelectedCopies) {
            if (c.getBarcode().equalsIgnoreCase(barcode)) {
                JOptionPane.showMessageDialog(this, 
                    "Cuốn sách có mã vạch '" + barcode + "' đã có trong danh sách mượn!", 
                    "Trùng lặp bản sao", JOptionPane.WARNING_MESSAGE);
                txtCopyBarcode.selectAll();
                return;
            }
        }

        // Kiểm tra tổng số sách vượt hạn mức
        int totalWillBorrow = currentBorrowedCount + listSelectedCopies.size() + 1;
        if (totalWillBorrow > currentReader.getMaxBorrowLimit()) {
            JOptionPane.showMessageDialog(this, 
                "Không thể thêm sách! Độc giả đang giữ " + currentBorrowedCount + " cuốn,\n"
                + "thêm cuốn này sẽ vượt quá hạn mức tối đa cho phép (" + currentReader.getMaxBorrowLimit() + " cuốn).", 
                "Vượt hạn mức mượn", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Truy xuất bản sao từ DAO
        DocumentCopy5 copy = copyDAO.getCopyByBarcode5(barcode);
        if (copy == null) {
            JOptionPane.showMessageDialog(this, 
                "Không tìm thấy bản sao tài liệu với mã vạch: '" + barcode + "' trong kho!", 
                "Mã vạch không tồn tại", JOptionPane.ERROR_MESSAGE);
            txtCopyBarcode.selectAll();
            return;
        }

        if (!copy.isAvailable()) {
            JOptionPane.showMessageDialog(this, 
                "Bản sao '" + copy.getBarcode() + "' hiện đang ở trạng thái: '" + copy.getStatus() + "'!\n"
                + "Chỉ có thể cho mượn tài liệu đang ở trạng thái 'Có sẵn'.", 
                "Tài liệu không khả dụng", JOptionPane.WARNING_MESSAGE);
            txtCopyBarcode.selectAll();
            return;
        }

        // Hợp lệ -> Thêm vào danh sách
        listSelectedCopies.add(copy);
        txtCopyBarcode.setText("");
        txtCopyBarcode.requestFocus();
        refreshTable();
    }

    /**
     * Xóa 1 cuốn sách đã quét khỏi bảng.
     */
    public void btnRemoveItemActionPerformed(ActionEvent e) {
        int selectedRow = tblBorrowItems.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < listSelectedCopies.size()) {
            listSelectedCopies.remove(selectedRow);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng sách cần xóa khỏi bảng!", 
                    "Chưa chọn dòng", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        Date dueDate = DateUtil5.addDays(new Date(), 14);
        String dueDateStr = DateUtil5.formatDate(dueDate);

        for (int i = 0; i < listSelectedCopies.size(); i++) {
            DocumentCopy5 c = listSelectedCopies.get(i);
            String docTitle = c.getDocument5() != null ? c.getDocument5().getTitle() : "Tài liệu #" + c.getDocumentId();
            String location = c.getLocationRack() != null ? c.getLocationRack() : "Ngăn chung";

            tableModel.addRow(new Object[]{
                i + 1,
                c.getBarcode(),
                docTitle,
                "#" + c.getCopyNumber(),
                location,
                c.getConditionStatus() != null ? c.getConditionStatus() : "Tốt",
                dueDateStr
            });
        }

        int totalCount = listSelectedCopies.size();
        lblSummaryTotal.setText("Tổng số tài liệu mượn trong phiếu: " + totalCount + " cuốn (Hạn trả mặc định: 14 ngày - " + dueDateStr + ")");
        btnConfirmLoan.setEnabled(totalCount > 0 && currentReader != null);
    }

    /**
     * Bước 3: Xác nhận lập phiếu mượn, lưu CSDL qua Transaction và mở màn hình in phiếu.
     */
    public void btnConfirmLoanActionPerformed(ActionEvent e) {
        if (currentReader == null || listSelectedCopies.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng quét thẻ bạn đọc và ít nhất 1 cuốn sách!", 
                    "Chưa đủ thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Xác nhận cho bạn đọc " + currentReader.getFullName() + " (" + currentReader.getReaderCode() + ")\n"
            + "mượn " + listSelectedCopies.size() + " cuốn sách?\n"
            + "Hạn trả: 14 ngày kể từ hôm nay.",
            "Xác nhận lập phiếu mượn", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Tạo đối tượng phiếu mượn
        currentBorrowSlip = new BorrowSlip5();
        currentBorrowSlip.setReader5(currentReader);
        currentBorrowSlip.setStaff5(currentStaff);
        currentBorrowSlip.setBorrowDate(new Date());
        currentBorrowSlip.setDueDate(DateUtil5.addDays(new Date(), 14));
        currentBorrowSlip.setStatus("Đang mượn");
        currentBorrowSlip.setDepositAmount(0.0);
        currentBorrowSlip.setNote("Phiếu mượn lập tại quầy dịch vụ thư viện");

        for (DocumentCopy5 copy : listSelectedCopies) {
            BorrowDetail5 detail = new BorrowDetail5();
            detail.setDocumentCopy5(copy);
            detail.setBorrowStatus("Đang mượn");
            detail.setFineAmount(0.0);
            detail.setConditionOnReturn(null);
            currentBorrowSlip.addBorrowDetail(detail);
        }

        // Thực thi transaction lưu CSDL
        boolean success = borrowSlipDAO.saveBorrowSlip5(currentBorrowSlip);

        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Lập phiếu mượn thành công!\nMã phiếu mượn: " + currentBorrowSlip.getSlipCode() + "\n"
                + "Đang chuyển sang màn hình In phiếu mượn cho bạn đọc...", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Mở màn hình In Phiếu mượn (PrintBorrowSlipFrm5)
            PrintBorrowSlipFrm5 printFrm = new PrintBorrowSlipFrm5(parentFrame, currentBorrowSlip);
            printFrm.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Lỗi giao dịch Transaction khi lưu phiếu mượn vào CSDL!\nDữ liệu chưa bị thay đổi.", 
                "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        }
    }
}

