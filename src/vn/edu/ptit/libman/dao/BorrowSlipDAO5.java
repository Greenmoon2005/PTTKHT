package vn.edu.ptit.libman.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import vn.edu.ptit.libman.model.BorrowDetail5;
import vn.edu.ptit.libman.model.BorrowSlip5;
import vn.edu.ptit.libman.model.DocumentCopy5;
import vn.edu.ptit.libman.util.MockDatabase5;

/**
 * Lớp BorrowSlipDAO5 - Xử lý lưu và truy xuất phiếu mượn tài liệu với Transaction an toàn.
 * So khớp hoàn toàn với Sơ đồ tuần tự và Sơ đồ lớp chi tiết Module 2 (Hậu tố 5 - B23DCCN015).
 */
public class BorrowSlipDAO5 extends DAO5 {

    private final DocumentCopyDAO5 copyDAO;

    public BorrowSlipDAO5() {
        super();
        this.copyDAO = new DocumentCopyDAO5();
    }

    /**
     * Lưu phiếu mượn và danh sách chi tiết mượn vào CSDL bằng Transaction.
     * Cập nhật trạng thái các bản sao vật lý tương ứng sang 'Đang mượn'.
     */
    public boolean saveBorrowSlip5(BorrowSlip5 slip) {
        if (slip == null || slip.getBorrowDetails5() == null || slip.getBorrowDetails5().isEmpty()) {
            return false;
        }

        // Tự động sinh mã phiếu mượn nếu chưa có
        if (slip.getSlipCode() == null || slip.getSlipCode().trim().isEmpty()) {
            String datePrefix = new SimpleDateFormat("yyyyMMdd").format(new Date());
            int randSuffix = 100 + new Random().nextInt(900);
            slip.setSlipCode("SLIP-" + datePrefix + "-" + randSuffix);
        }

        ensureConnection();

        if (this.conn != null) {
            String sqlSlip = "INSERT INTO tblBorrowSlip5 (slip_code, reader_id, staff_id, borrow_date, due_date, status, deposit_amount, note) "
                           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            String sqlDetail = "INSERT INTO tblBorrowDetail5 (borrow_slip_id, copy_id, borrow_status, return_date, fine_amount, condition_on_return) "
                             + "VALUES (?, ?, ?, ?, ?, ?)";
            String sqlUpdateCopy = "UPDATE tblDocumentCopy5 SET status = 'Đang mượn' WHERE id = ?";

            try {
                this.conn.setAutoCommit(false); // Bắt đầu Transaction

                int generatedSlipId = -1;
                try (PreparedStatement psSlip = this.conn.prepareStatement(sqlSlip, Statement.RETURN_GENERATED_KEYS)) {
                    psSlip.setString(1, slip.getSlipCode());
                    psSlip.setInt(2, slip.getReader5() != null ? slip.getReader5().getId() : 1);
                    psSlip.setInt(3, slip.getStaff5() != null ? slip.getStaff5().getId() : 4);
                    psSlip.setTimestamp(4, new Timestamp(slip.getBorrowDate() != null ? slip.getBorrowDate().getTime() : System.currentTimeMillis()));
                    psSlip.setTimestamp(5, new Timestamp(slip.getDueDate() != null ? slip.getDueDate().getTime() : System.currentTimeMillis() + 14L * 86400000L));
                    psSlip.setString(6, slip.getStatus() != null ? slip.getStatus() : "Đang mượn");
                    psSlip.setDouble(7, slip.getDepositAmount());
                    psSlip.setString(8, slip.getNote() != null ? slip.getNote() : "");

                    int affected = psSlip.executeUpdate();
                    if (affected == 0) {
                        this.conn.rollback();
                        return false;
                    }

                    try (ResultSet rsKeys = psSlip.getGeneratedKeys()) {
                        if (rsKeys.next()) {
                            generatedSlipId = rsKeys.getInt(1);
                            slip.setId(generatedSlipId);
                        }
                    }
                }

                // Lưu từng bản sao chi tiết và cập nhật trạng thái bản sao
                try (PreparedStatement psDetail = this.conn.prepareStatement(sqlDetail);
                     PreparedStatement psUpdateCopy = this.conn.prepareStatement(sqlUpdateCopy)) {

                    for (BorrowDetail5 detail : slip.getBorrowDetails5()) {
                        psDetail.setInt(1, generatedSlipId);
                        psDetail.setInt(2, detail.getDocumentCopy5().getId());
                        psDetail.setString(3, "Đang mượn");
                        psDetail.setNull(4, java.sql.Types.TIMESTAMP);
                        psDetail.setDouble(5, 0.0);
                        psDetail.setString(6, detail.getDocumentCopy5().getConditionStatus());
                        psDetail.addBatch();

                        psUpdateCopy.setInt(1, detail.getDocumentCopy5().getId());
                        psUpdateCopy.addBatch();
                    }

                    psDetail.executeBatch();
                    psUpdateCopy.executeBatch();
                }

                this.conn.commit(); // Transaction thành công
                this.conn.setAutoCommit(true);
                System.out.println("[BorrowSlipDAO5] Đã lưu phiếu mượn " + slip.getSlipCode() + " thành công vào MySQL!");
                return true;

            } catch (SQLException e) {
                System.err.println("[BorrowSlipDAO5] Lỗi Transaction khi lưu phiếu mượn: " + e.getMessage());
                try {
                    this.conn.rollback();
                    this.conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    System.err.println("[BorrowSlipDAO5] Rollback thất bại: " + ex.getMessage());
                }
                return false;
            }
        }

        // Fallback In-Memory
        slip.setId(MockDatabase5.getInstance().borrowSlips.size() + 1);
        for (BorrowDetail5 d : slip.getBorrowDetails5()) {
            d.setBorrowSlip5(slip);
            DocumentCopy5 copy = d.getDocumentCopy5();
            if (copy != null) {
                copy.setStatus("Đang mượn");
            }
        }
        MockDatabase5.getInstance().borrowSlips.add(slip);
        System.out.println("[BorrowSlipDAO5 - In-Memory] Đã lưu phiếu mượn " + slip.getSlipCode() + " vào bộ nhớ mô phỏng!");
        return true;
    }

    /**
     * Tìm phiếu mượn theo mã code.
     */
    public BorrowSlip5 getBorrowSlipByCode5(String code) {
        if (code == null || code.trim().isEmpty()) return null;
        String trimmed = code.trim();

        // Fallback In-Memory
        for (BorrowSlip5 s : MockDatabase5.getInstance().borrowSlips) {
            if (s.getSlipCode().equalsIgnoreCase(trimmed)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Lấy phiếu mượn theo ID.
     */
    public BorrowSlip5 getSlipById5(int slipId) {
        for (BorrowSlip5 s : MockDatabase5.getInstance().borrowSlips) {
            if (s.getId() == slipId) {
                return s;
            }
        }
        return null;
    }

    /**
     * Đếm số sách đang mượn của độc giả.
     */
    public int getActiveBorrowCountByReader5(int readerId) {
        return new ReaderDAO5().getCurrentBorrowedCount5(readerId);
    }

    private void ensureConnection() {
        if (this.conn == null) {
            this.conn = getConnection();
        }
    }
}

