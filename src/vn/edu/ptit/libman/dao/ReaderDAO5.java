package vn.edu.ptit.libman.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import vn.edu.ptit.libman.model.Card5;
import vn.edu.ptit.libman.model.Reader5;
import vn.edu.ptit.libman.util.MockDatabase5;

/**
 * Lớp ReaderDAO5 - Quản lý truy xuất dữ liệu độc giả và thẻ thư viện (tblReader5, tblCard5).
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 2 (Hậu tố 5 - B23DCCN015).
 */
public class ReaderDAO5 extends DAO5 {

    public ReaderDAO5() {
        super();
    }

    /**
     * Tìm kiếm bạn đọc theo mã vạch trên thẻ (barcode quét từ máy đọc mã vạch).
     */
    public Reader5 getReaderByCardBarcode5(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) return null;
        String trimmed = barcode.trim();
        ensureConnection();

        if (this.conn != null) {
            String sql = "SELECT u.id, u.username, u.password, u.full_name, u.dob, u.gender, u.email, u.phone, u.address, u.role, "
                       + "r.reader_code, r.faculty, r.student_class, r.expiry_date, r.max_borrow_limit, r.active, "
                       + "c.id AS card_id, c.card_code, c.issue_date AS card_issue, c.expire_date AS card_expire, c.status AS card_status, c.barcode "
                       + "FROM tblCard5 c "
                       + "JOIN tblReader5 r ON c.reader_id = r.user_id "
                       + "JOIN tblUser5 u ON r.user_id = u.id "
                       + "WHERE c.barcode = ? OR c.card_code = ? OR r.reader_code = ?";
            try (PreparedStatement ps = this.conn.prepareStatement(sql)) {
                ps.setString(1, trimmed);
                ps.setString(2, trimmed);
                ps.setString(3, trimmed);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return extractReaderFromResultSet(rs);
                    }
                }
            } catch (SQLException e) {
                System.err.println("[ReaderDAO5] Lỗi truy vấn getReaderByCardBarcode5: " + e.getMessage());
            }
        }

        // Fallback In-Memory
        for (Reader5 r : MockDatabase5.getInstance().readers) {
            if (r.getCard5() != null) {
                if (r.getCard5().getBarcode().equalsIgnoreCase(trimmed) 
                        || r.getCard5().getCardCode().equalsIgnoreCase(trimmed)
                        || r.getReaderCode().equalsIgnoreCase(trimmed)) {
                    return r;
                }
            }
        }
        return null;
    }

    /**
     * Alias method theo sơ đồ Package Diagram: getReaderByCardCode5.
     */
    public Reader5 getReaderByCardCode5(String cardCode) {
        return getReaderByCardBarcode5(cardCode);
    }

    /**
     * Lấy thông tin bạn đọc theo User ID.
     */
    public Reader5 getReaderById5(int id) {
        ensureConnection();
        if (this.conn != null) {
            String sql = "SELECT u.id, u.username, u.password, u.full_name, u.dob, u.gender, u.email, u.phone, u.address, u.role, "
                       + "r.reader_code, r.faculty, r.student_class, r.expiry_date, r.max_borrow_limit, r.active, "
                       + "c.id AS card_id, c.card_code, c.issue_date AS card_issue, c.expire_date AS card_expire, c.status AS card_status, c.barcode "
                       + "FROM tblReader5 r "
                       + "JOIN tblUser5 u ON r.user_id = u.id "
                       + "LEFT JOIN tblCard5 c ON c.reader_id = r.user_id "
                       + "WHERE r.user_id = ?";
            try (PreparedStatement ps = this.conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return extractReaderFromResultSet(rs);
                    }
                }
            } catch (SQLException e) {
                System.err.println("[ReaderDAO5] Lỗi truy vấn getReaderById5: " + e.getMessage());
            }
        }

        // Fallback In-Memory
        for (Reader5 r : MockDatabase5.getInstance().readers) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    /**
     * Kiểm tra điều kiện hợp lệ để mượn sách của độc giả:
     * 1. Tài khoản độc giả đang active
     * 2. Thẻ thư viện còn hạn và trạng thái Active
     * 3. Số sách đang mượn chưa chạm ngưỡng tối đa (5 cuốn)
     */
    public boolean checkReaderEligibility5(int readerId) {
        Reader5 reader = getReaderById5(readerId);
        if (reader == null || !reader.isActive()) {
            return false;
        }
        if (reader.getCard5() == null || !reader.getCard5().isValid()) {
            return false;
        }
        int borrowedCount = getCurrentBorrowedCount5(readerId);
        return borrowedCount < reader.getMaxBorrowLimit();
    }

    /**
     * Đếm tổng số cuốn sách bạn đọc này đang mượn chưa trả.
     */
    public int getCurrentBorrowedCount5(int readerId) {
        ensureConnection();
        if (this.conn != null) {
            String sql = "SELECT COUNT(bd.id) AS active_count "
                       + "FROM tblBorrowDetail5 bd "
                       + "JOIN tblBorrowSlip5 s ON bd.borrow_slip_id = s.id "
                       + "WHERE s.reader_id = ? AND bd.borrow_status = 'Đang mượn'";
            try (PreparedStatement ps = this.conn.prepareStatement(sql)) {
                ps.setInt(1, readerId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("active_count");
                    }
                }
            } catch (SQLException e) {
                System.err.println("[ReaderDAO5] Lỗi truy vấn getCurrentBorrowedCount5: " + e.getMessage());
            }
        }

        // Fallback In-Memory
        int count = 0;
        for (var slip : MockDatabase5.getInstance().borrowSlips) {
            if (slip.getReader5() != null && slip.getReader5().getId() == readerId) {
                if ("Đang mượn".equalsIgnoreCase(slip.getStatus())) {
                    count += slip.getDetailCount();
                }
            }
        }
        return count;
    }

    /**
     * Alias method: getActiveBorrowCountByReader5.
     */
    public int getActiveBorrowCountByReader5(int readerId) {
        return getCurrentBorrowedCount5(readerId);
    }

    private Reader5 extractReaderFromResultSet(ResultSet rs) throws SQLException {
        Reader5 r = new Reader5();
        r.setId(rs.getInt("id"));
        r.setUsername(rs.getString("username"));
        r.setPassword(rs.getString("password"));
        r.setFullName(rs.getString("full_name"));
        r.setDateOfBirth(rs.getDate("dob"));
        r.setGender(rs.getString("gender"));
        r.setEmail(rs.getString("email"));
        r.setPhone(rs.getString("phone"));
        r.setAddress(rs.getString("address"));
        r.setRole(rs.getString("role"));

        r.setReaderCode(rs.getString("reader_code"));
        r.setFaculty(rs.getString("faculty"));
        r.setStudentClass(rs.getString("student_class"));
        r.setExpiryDate(rs.getDate("expiry_date"));
        r.setMaxBorrowLimit(rs.getInt("max_borrow_limit"));
        r.setActive(rs.getBoolean("active"));

        int cardId = rs.getInt("card_id");
        if (cardId > 0) {
            Card5 card = new Card5();
            card.setId(cardId);
            card.setCardCode(rs.getString("card_code"));
            card.setReaderId(r.getId());
            card.setIssueDate(rs.getDate("card_issue"));
            card.setExpireDate(rs.getDate("card_expire"));
            card.setStatus(rs.getString("card_status"));
            card.setBarcode(rs.getString("barcode"));
            r.setCard5(card);
        }

        return r;
    }

    private void ensureConnection() {
        if (this.conn == null) {
            this.conn = getConnection();
        }
    }
}

