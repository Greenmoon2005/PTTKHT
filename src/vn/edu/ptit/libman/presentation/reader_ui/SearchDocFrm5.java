package vn.edu.ptit.libman.presentation.reader_ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vn.edu.ptit.libman.dao.CategoryDAO5;
import vn.edu.ptit.libman.dao.DocumentDAO5;
import vn.edu.ptit.libman.model.Category5;
import vn.edu.ptit.libman.model.Document5;

/**
 * Màn hình Tra cứu & Tìm kiếm Tài liệu (SearchDocFrm5 / gdTimKiemTL5).
 * Module 1: Bạn đọc tìm thông tin tài liệu.
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết, Sơ đồ tuần tự và Kịch bản Use Case (Hậu tố 5 - B23DCCN015).
 */
public class SearchDocFrm5 extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField txtKeyword;
    private JComboBox<Category5> cmbCategory;
    private JButton btnSearch;
    private JButton btnReset;
    private JTable tblResults;
    private DefaultTableModel tableModel;
    private JButton btnViewDetail;
    private JButton btnBack;
    private JLabel lblStatus;

    private List<Document5> listDocs;
    private Document5 selectedDoc;

    private final CategoryDAO5 categoryDAO;
    private final DocumentDAO5 documentDAO;
    private final JFrame parentFrame;

    public SearchDocFrm5() {
        this(null);
    }

    public SearchDocFrm5(JFrame parent) {
        this.parentFrame = parent;
        this.categoryDAO = new CategoryDAO5();
        this.documentDAO = new DocumentDAO5();
        this.listDocs = new ArrayList<>();
        initComponents();
        loadCategories();
        // Nạp danh sách ban đầu
        performSearch();
    }

    private void initComponents() {
        setTitle("MODULE 1: TRA CỨU & TÌM KIẾM TÀI LIỆU (MÃ SV: B23DCCN015 - HẬU TỐ 5)");
        setSize(1000, 680);
        setMinimumSize(new Dimension(850, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Header (Navy)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(0x1B, 0x36, 0x5D));
        pnlHeader.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel lblTitle = new JLabel("TRA CỨU & TÌM KIẾM HỌC LIỆU THƯ VIỆN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitle.setForeground(Color.WHITE);

        btnBack = new JButton("⬅ Quay lại Trang chủ");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnBack.setBackground(new Color(0x2B, 0x6C, 0xB0));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(this::btnBackActionPerformed);

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(btnBack, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // 2. Main Content
        JPanel pnlMain = new JPanel(new BorderLayout(10, 10));
        pnlMain.setBackground(new Color(0xF8, 0xFA, 0xFC));
        pnlMain.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Filter / Search Criteria Box
        JPanel pnlFilter = new JPanel(new GridBagLayout());
        pnlFilter.setBackground(Color.WHITE);
        pnlFilter.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xCB, 0xD5, 0xE1), 1),
                " Tiêu chí tìm kiếm ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(0x1B, 0x36, 0x5D)
            ),
            new EmptyBorder(12, 16, 12, 16)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblKw = new JLabel("Từ khóa tìm kiếm:");
        lblKw.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        pnlFilter.add(lblKw, gbc);

        txtKeyword = new JTextField(25);
        txtKeyword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtKeyword.setToolTipText("Nhập tên tài liệu, mã tài liệu hoặc tên tác giả...");
        txtKeyword.addActionListener(this::btnSearchActionPerformed);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        pnlFilter.add(txtKeyword, gbc);

        JLabel lblCat = new JLabel("Thể loại / Chuyên mục:");
        lblCat.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0;
        pnlFilter.add(lblCat, gbc);

        cmbCategory = new JComboBox<>();
        cmbCategory.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbCategory.setBackground(Color.WHITE);
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0.8;
        pnlFilter.add(cmbCategory, gbc);

        btnSearch = new JButton("🔍 Tìm kiếm");
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSearch.setBackground(new Color(0x1B, 0x36, 0x5D));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(this::btnSearchActionPerformed);
        gbc.gridx = 4; gbc.gridy = 0; gbc.weightx = 0;
        pnlFilter.add(btnSearch, gbc);

        btnReset = new JButton("🔄 Đặt lại");
        btnReset.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnReset.setBackground(new Color(0xE2, 0xE8, 0xF0));
        btnReset.setForeground(new Color(0x1E, 0x29, 0x3B));
        btnReset.setFocusPainted(false);
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReset.addActionListener(this::btnResetActionPerformed);
        gbc.gridx = 5; gbc.gridy = 0; gbc.weightx = 0;
        pnlFilter.add(btnReset, gbc);

        pnlMain.add(pnlFilter, BorderLayout.NORTH);

        // Results Table
        String[] columnNames = {"STT", "Mã tài liệu", "Tên tài liệu / Tựa sách", "Thể loại", "Tác giả", "Năm XB", "Kệ sách", "Số bản sẵn có"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Chỉ xem
            }
        };

        tblResults = new JTable(tableModel);
        tblResults.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblResults.setRowHeight(28);
        tblResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblResults.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblResults.getTableHeader().setBackground(new Color(0x1B, 0x36, 0x5D));
        tblResults.getTableHeader().setForeground(Color.WHITE);
        tblResults.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        tblResults.getColumnModel().getColumn(0).setPreferredWidth(45);
        tblResults.getColumnModel().getColumn(1).setPreferredWidth(110);
        tblResults.getColumnModel().getColumn(2).setPreferredWidth(280);
        tblResults.getColumnModel().getColumn(3).setPreferredWidth(160);
        tblResults.getColumnModel().getColumn(4).setPreferredWidth(150);
        tblResults.getColumnModel().getColumn(5).setPreferredWidth(70);
        tblResults.getColumnModel().getColumn(6).setPreferredWidth(110);
        tblResults.getColumnModel().getColumn(7).setPreferredWidth(100);

        // Center align STT, Năm, Bản sẵn
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblResults.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblResults.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tblResults.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

        tblResults.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tblResultsMouseClicked(e);
            }
        });

        JScrollPane scrollTable = new JScrollPane(tblResults);
        scrollTable.setBorder(BorderFactory.createLineBorder(new Color(0xCB, 0xD5, 0xE1), 1));
        pnlMain.add(scrollTable, BorderLayout.CENTER);

        // Bottom Action Bar
        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(new EmptyBorder(8, 0, 0, 0));

        lblStatus = new JLabel("Nhấp đúp chuột vào một dòng hoặc chọn dòng rồi bấm 'Xem chi tiết' để xem các bản sao.");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblStatus.setForeground(new Color(0x64, 0x74, 0x8B));

        btnViewDetail = new JButton("📖 Xem chi tiết & Tình trạng bản sao");
        btnViewDetail.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnViewDetail.setBackground(new Color(0x2B, 0x6C, 0xB0));
        btnViewDetail.setForeground(Color.WHITE);
        btnViewDetail.setFocusPainted(false);
        btnViewDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewDetail.setEnabled(false);
        btnViewDetail.addActionListener(this::btnViewDetailActionPerformed);

        pnlBottom.add(lblStatus, BorderLayout.WEST);
        pnlBottom.add(btnViewDetail, BorderLayout.EAST);
        pnlMain.add(pnlBottom, BorderLayout.SOUTH);

        add(pnlMain, BorderLayout.CENTER);
    }

    private void loadCategories() {
        cmbCategory.removeAllItems();
        // Item mặc định "Tất cả thể loại"
        Category5 allCat = new Category5(0, "ALL", "--- Tất cả thể loại ---", "");
        cmbCategory.addItem(allCat);

        List<Category5> cats = categoryDAO.getAllCategories5();
        for (Category5 c : cats) {
            cmbCategory.addItem(c);
        }
    }

    private void performSearch() {
        String keyword = txtKeyword.getText();
        Category5 selectedCat = (Category5) cmbCategory.getSelectedItem();
        int catId = selectedCat != null ? selectedCat.getId() : 0;

        listDocs = documentDAO.searchDocument5(keyword, catId);
        displayResults(listDocs);
    }

    public void displayResults(List<Document5> docs) {
        tableModel.setRowCount(0);
        selectedDoc = null;
        btnViewDetail.setEnabled(false);

        if (docs == null || docs.isEmpty()) {
            lblStatus.setText("Không tìm thấy tài liệu phù hợp. Vui lòng thử từ khóa khác.");
            lblStatus.setForeground(new Color(0xDC, 0x26, 0x26));
            return;
        }

        lblStatus.setText("Tìm thấy " + docs.size() + " tài liệu phù hợp với điều kiện.");
        lblStatus.setForeground(new Color(0x16, 0xA3, 0x4A));

        for (int i = 0; i < docs.size(); i++) {
            Document5 d = docs.get(i);
            String catName = d.getCategory5() != null ? d.getCategory5().getName() : "Khác";
            String authors = d.getAuthorNames();
            int availCount = d.getAvailableCopiesCount();

            tableModel.addRow(new Object[]{
                i + 1,
                d.getDocCode(),
                d.getTitle(),
                catName,
                authors,
                d.getPublishYear() > 0 ? d.getPublishYear() : "-",
                d.getLocationShelf() != null ? d.getLocationShelf() : "Đang cập nhật",
                availCount + " cuốn"
            });
        }
    }

    public void btnSearchActionPerformed(ActionEvent e) {
        performSearch();
    }

    public void btnResetActionPerformed(ActionEvent e) {
        txtKeyword.setText("");
        if (cmbCategory.getItemCount() > 0) {
            cmbCategory.setSelectedIndex(0);
        }
        performSearch();
    }

    public void tblResultsMouseClicked(MouseEvent e) {
        int selectedRow = tblResults.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < listDocs.size()) {
            selectedDoc = listDocs.get(selectedRow);
            btnViewDetail.setEnabled(true);

            // Double click opens detail screen immediately
            if (e.getClickCount() == 2) {
                btnViewDetailActionPerformed(null);
            }
        }
    }

    public void btnViewDetailActionPerformed(ActionEvent e) {
        if (selectedDoc == null) {
            int selectedRow = tblResults.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < listDocs.size()) {
                selectedDoc = listDocs.get(selectedRow);
            }
        }

        if (selectedDoc != null) {
            // Mở màn hình Chi tiết Tài liệu & Tình trạng Bản sao (DocDetailFrm5)
            DocDetailFrm5 detailFrm = new DocDetailFrm5(this, selectedDoc.getId());
            detailFrm.setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài liệu trong bảng để xem chi tiết!", 
                    "Chưa chọn tài liệu", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void btnBackActionPerformed(ActionEvent e) {
        if (parentFrame != null) {
            parentFrame.setVisible(true);
        }
        dispose();
    }
}

