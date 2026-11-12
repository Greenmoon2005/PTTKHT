package vn.edu.ptit.libman.presentation.reader_ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vn.edu.ptit.libman.dao.DocumentCopyDAO5;
import vn.edu.ptit.libman.dao.DocumentDAO5;
import vn.edu.ptit.libman.model.Document5;
import vn.edu.ptit.libman.model.DocumentCopy5;

/**
 * Màn hình Chi tiết Tài liệu & Tình trạng Bản sao (DocDetailFrm5 / gdChiTietTL5).
 * Module 1: Bạn đọc tìm thông tin tài liệu.
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết, Sơ đồ tuần tự và UI Mockup (Hậu tố 5 - B23DCCN015).
 */
public class DocDetailFrm5 extends JFrame {
    private static final long serialVersionUID = 1L;

    private JLabel lblTitle;
    private JLabel lblDocCode;
    private JLabel lblCategory;
    private JLabel lblPublisher;
    private JLabel lblPublishYear;
    private JLabel lblLocation;
    private JLabel lblAuthors;
    private JLabel lblPrice;
    private JTextArea txtSummary;
    private JTable tblCopies;
    private DefaultTableModel tableModelCopies;
    private JButton btnBack;

    private Document5 currentDoc;
    private final DocumentDAO5 documentDAO;
    private final DocumentCopyDAO5 copyDAO;
    private final JFrame parentFrame;

    public DocDetailFrm5(JFrame parent, int docId) {
        this.parentFrame = parent;
        this.documentDAO = new DocumentDAO5();
        this.copyDAO = new DocumentCopyDAO5();
        initComponents();
        loadDocumentDetails(docId);
    }

    private void initComponents() {
        setTitle("CHI TIẾT TÀI LIỆU & TÌNH TRẠNG BẢN SAO (MÃ SV: B23DCCN015 - HẬU TỐ 5)");
        setSize(950, 680);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(0x1B, 0x36, 0x5D));
        pnlHeader.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel lblHeader = new JLabel("THÔNG TIN CHI TIẾT TÀI LIỆU VÀ TÌNH TRẠNG CÁC BẢN SAO");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblHeader.setForeground(Color.WHITE);

        btnBack = new JButton("⬅ Quay lại Tìm kiếm");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnBack.setBackground(new Color(0x2B, 0x6C, 0xB0));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(this::btnBackActionPerformed);

        pnlHeader.add(lblHeader, BorderLayout.WEST);
        pnlHeader.add(btnBack, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // Body Panel
        JPanel pnlBody = new JPanel();
        pnlBody.setLayout(new BoxLayout(pnlBody, BoxLayout.Y_AXIS));
        pnlBody.setBackground(new Color(0xF8, 0xFA, 0xFC));
        pnlBody.setBorder(new EmptyBorder(15, 20, 15, 20));

        // 1. Info Card
        JPanel pnlInfo = new JPanel(new GridBagLayout());
        pnlInfo.setBackground(Color.WHITE);
        pnlInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xCB, 0xD5, 0xE1), 1),
                " 1. Thông tin thư mục & siêu dữ liệu tựa sách ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), new Color(0x1B, 0x36, 0x5D)
            ),
            new EmptyBorder(12, 16, 12, 16)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Row
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        pnlInfo.add(createBoldLabel("Tên tài liệu:"), gbc);

        lblTitle = createValueLabel("Đang tải...");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(new Color(0x1B, 0x36, 0x5D));
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 3; gbc.weightx = 1.0;
        pnlInfo.add(lblTitle, gbc);
        gbc.gridwidth = 1;

        // Code and Category Row
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        pnlInfo.add(createBoldLabel("Mã tài liệu:"), gbc);
        lblDocCode = createValueLabel("N/A");
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.5;
        pnlInfo.add(lblDocCode, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0;
        pnlInfo.add(createBoldLabel("Thể loại:"), gbc);
        lblCategory = createValueLabel("N/A");
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 0.5;
        pnlInfo.add(lblCategory, gbc);

        // Authors and Publisher Row
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        pnlInfo.add(createBoldLabel("Tác giả:"), gbc);
        lblAuthors = createValueLabel("N/A");
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.5;
        pnlInfo.add(lblAuthors, gbc);

        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0;
        pnlInfo.add(createBoldLabel("Nhà xuất bản:"), gbc);
        lblPublisher = createValueLabel("N/A");
        gbc.gridx = 3; gbc.gridy = 2; gbc.weightx = 0.5;
        pnlInfo.add(lblPublisher, gbc);

        // Year, Shelf, Price Row
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        pnlInfo.add(createBoldLabel("Năm XB:"), gbc);
        lblPublishYear = createValueLabel("N/A");
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.5;
        pnlInfo.add(lblPublishYear, gbc);

        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = 0;
        pnlInfo.add(createBoldLabel("Vị trí kệ sách:"), gbc);
        lblLocation = createValueLabel("N/A");
        lblLocation.setForeground(new Color(0x04, 0x78, 0x57));
        gbc.gridx = 3; gbc.gridy = 3; gbc.weightx = 0.5;
        pnlInfo.add(lblLocation, gbc);

        // Summary Row
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        pnlInfo.add(createBoldLabel("Tóm tắt nội dung:"), gbc);

        txtSummary = new JTextArea(3, 40);
        txtSummary.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSummary.setLineWrap(true);
        txtSummary.setWrapStyleWord(true);
        txtSummary.setEditable(false);
        txtSummary.setBackground(new Color(0xF8, 0xFA, 0xFC));
        txtSummary.setBorder(BorderFactory.createLineBorder(new Color(0xE2, 0xE8, 0xF0), 1));
        JScrollPane scrollSummary = new JScrollPane(txtSummary);
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 3; gbc.weightx = 1.0;
        pnlInfo.add(scrollSummary, gbc);

        pnlBody.add(pnlInfo);
        pnlBody.add(Box.createVerticalStrut(15));

        // 2. Copies Table Card
        JPanel pnlCopies = new JPanel(new BorderLayout(5, 5));
        pnlCopies.setBackground(Color.WHITE);
        pnlCopies.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xCB, 0xD5, 0xE1), 1),
                " 2. Danh sách bản sao vật lý & Tình trạng khả dụng tại kho (tblDocumentCopy5) ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13), new Color(0x1B, 0x36, 0x5D)
            ),
            new EmptyBorder(10, 15, 10, 15)
        ));

        String[] copyCols = {"STT", "Mã vạch (Barcode)", "Bản sao số", "Năm in", "Trạng thái mượn", "Tình trạng sách", "Vị trí ngăn/giá"};
        tableModelCopies = new DefaultTableModel(copyCols, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblCopies = new JTable(tableModelCopies);
        tblCopies.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblCopies.setRowHeight(26);
        tblCopies.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblCopies.getTableHeader().setBackground(new Color(0x1B, 0x36, 0x5D));
        tblCopies.getTableHeader().setForeground(Color.WHITE);

        // Center renderers
        DefaultTableCellRenderer centerR = new DefaultTableCellRenderer();
        centerR.setHorizontalAlignment(JLabel.CENTER);
        tblCopies.getColumnModel().getColumn(0).setCellRenderer(centerR);
        tblCopies.getColumnModel().getColumn(2).setCellRenderer(centerR);
        tblCopies.getColumnModel().getColumn(3).setCellRenderer(centerR);

        // Color renderer for status
        tblCopies.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                String st = value != null ? value.toString() : "";
                if ("Có sẵn".equalsIgnoreCase(st)) {
                    setForeground(new Color(0x16, 0xA3, 0x4A));
                    setFont(getFont().deriveFont(Font.BOLD));
                } else if ("Đang mượn".equalsIgnoreCase(st)) {
                    setForeground(new Color(0xDC, 0x26, 0x26));
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setForeground(new Color(0xD9, 0x77, 0x06));
                }
                return c;
            }
        });

        JScrollPane scrollCopies = new JScrollPane(tblCopies);
        scrollCopies.setPreferredSize(new Dimension(800, 180));
        pnlCopies.add(scrollCopies, BorderLayout.CENTER);

        JLabel lblCopyTip = new JLabel("Ghi chú: Bản sao ở trạng thái 'Có sẵn' có thể tới trực tiếp quầy lưu thông để nhờ thủ thư làm thủ tục cho mượn.");
        lblCopyTip.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblCopyTip.setForeground(new Color(0x64, 0x74, 0x8B));
        pnlCopies.add(lblCopyTip, BorderLayout.SOUTH);

        pnlBody.add(pnlCopies);

        add(pnlBody, BorderLayout.CENTER);
    }

    private JLabel createBoldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(0x33, 0x41, 0x55));
        return l;
    }

    private JLabel createValueLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setForeground(new Color(0x0F, 0x17, 0x2A));
        return l;
    }

    public void loadDocumentDetails(int docId) {
        currentDoc = documentDAO.getDocumentById5(docId);
        if (currentDoc != null) {
            lblTitle.setText(currentDoc.getTitle());
            lblDocCode.setText(currentDoc.getDocCode());
            lblCategory.setText(currentDoc.getCategory5() != null ? currentDoc.getCategory5().getName() : "Khác");
            lblPublisher.setText(currentDoc.getPublisher5() != null ? currentDoc.getPublisher5().getName() : "N/A");
            lblPublishYear.setText(currentDoc.getPublishYear() > 0 ? String.valueOf(currentDoc.getPublishYear()) : "N/A");
            lblLocation.setText(currentDoc.getLocationShelf() != null ? currentDoc.getLocationShelf() : "Kho chung");
            lblAuthors.setText(currentDoc.getAuthorNames());
            txtSummary.setText(currentDoc.getSummary() != null ? currentDoc.getSummary() : "Chưa có bản tóm tắt.");

            // Tải danh sách bản sao
            List<DocumentCopy5> copies = copyDAO.getCopiesByDocumentId5(docId);
            displayCopiesTable(copies);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin tài liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void displayCopiesTable(List<DocumentCopy5> copies) {
        tableModelCopies.setRowCount(0);
        if (copies == null || copies.isEmpty()) {
            return;
        }

        for (int i = 0; i < copies.size(); i++) {
            DocumentCopy5 c = copies.get(i);
            tableModelCopies.addRow(new Object[]{
                i + 1,
                c.getBarcode(),
                "Bản sao #" + c.getCopyNumber(),
                c.getPrintYear() > 0 ? c.getPrintYear() : "-",
                c.getStatus(),
                c.getConditionStatus() != null ? c.getConditionStatus() : "Bình thường",
                c.getLocationRack() != null ? c.getLocationRack() : "Ngăn lưu trữ chung"
            });
        }
    }

    public void btnBackActionPerformed(ActionEvent e) {
        if (parentFrame != null) {
            parentFrame.setVisible(true);
        }
        dispose();
    }
}

