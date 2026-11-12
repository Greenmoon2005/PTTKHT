package vn.edu.ptit.libman.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vn.edu.ptit.libman.model.Document5;
import vn.edu.ptit.libman.model.DocumentCopy5;
import vn.edu.ptit.libman.util.MockDatabase5;

/**
 * Lớp DocumentCopyDAO5 - Quản lý các bản sao tài liệu vật lý (tblDocumentCopy5).
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 1, Module 2 (Hậu tố 5 - B23DCCN015).
 */
public class DocumentCopyDAO5 extends DAO5 {

    public DocumentCopyDAO5() {
        super();
    }

    /**
     * Lấy toàn bộ danh sách bản sao vật lý của một đầu tài liệu (theo documentId).
     * Phục vụ Module 1: Hiển thị bảng tblCopies trong DocDetailFrm5.
     */
    public List<DocumentCopy5> getCopiesByDocumentId5(int docId) {
        List<DocumentCopy5> list = new ArrayList<>();
        ensureConnection();

        if (this.conn != null) {
            String sql = "SELECT id, barcode, document_id, copy_number, print_year, status, condition_status, location_rack "
                       + "FROM tblDocumentCopy5 WHERE document_id = ? ORDER BY copy_number ASC";
            try (PreparedStatement ps = this.conn.prepareStatement(sql)) {
                ps.setInt(1, docId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        DocumentCopy5 copy = new DocumentCopy5();
                        copy.setId(rs.getInt("id"));
                        copy.setBarcode(rs.getString("barcode"));
                        copy.setDocumentId(rs.getInt("document_id"));
                        copy.setCopyNumber(rs.getInt("copy_number"));
                        copy.setPrintYear(rs.getInt("print_year"));
                        copy.setStatus(rs.getString("status"));
                        copy.setConditionStatus(rs.getString("condition_status"));
                        copy.setLocationRack(rs.getString("location_rack"));
                        list.add(copy);
                    }
                    return list;
                }
            } catch (SQLException e) {
                System.err.println("[DocumentCopyDAO5] Lỗi truy vấn getCopiesByDocumentId5: " + e.getMessage());
            }
        }

        // Fallback In-Memory
        for (DocumentCopy5 c : MockDatabase5.getInstance().documentCopies) {
            if (c.getDocumentId() == docId) {
                list.add(c);
            }
        }
        return list;
    }

    /**
     * Alias method theo sơ đồ Package Diagram: getCopiesByDocId5.
     */
    public List<DocumentCopy5> getCopiesByDocId5(int docId) {
        return getCopiesByDocumentId5(docId);
    }

    /**
     * Lấy thông tin bản sao tài liệu theo mã vạch barcode (kèm thông tin tựa sách).
     */
    public DocumentCopy5 getCopyByBarcode5(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) return null;
        String trimmed = barcode.trim();
        ensureConnection();

        if (this.conn != null) {
            String sql = "SELECT c.id, c.barcode, c.document_id, c.copy_number, c.print_year, "
                       + "c.status, c.condition_status, c.location_rack, "
                       + "d.title, d.doc_code, d.location_shelf "
                       + "FROM tblDocumentCopy5 c "
                       + "JOIN tblDocument5 d ON c.document_id = d.id "
                       + "WHERE c.barcode = ?";
            try (PreparedStatement ps = this.conn.prepareStatement(sql)) {
                ps.setString(1, trimmed);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        DocumentCopy5 copy = new DocumentCopy5();
                        copy.setId(rs.getInt("id"));
                        copy.setBarcode(rs.getString("barcode"));
                        copy.setDocumentId(rs.getInt("document_id"));
                        copy.setCopyNumber(rs.getInt("copy_number"));
                        copy.setPrintYear(rs.getInt("print_year"));
                        copy.setStatus(rs.getString("status"));
                        copy.setConditionStatus(rs.getString("condition_status"));
                        copy.setLocationRack(rs.getString("location_rack"));

                        Document5 doc = new Document5();
                        doc.setId(copy.getDocumentId());
                        doc.setTitle(rs.getString("title"));
                        doc.setDocCode(rs.getString("doc_code"));
                        doc.setLocationShelf(rs.getString("location_shelf"));
                        copy.setDocument5(doc);

                        return copy;
                    }
                }
            } catch (SQLException e) {
                System.err.println("[DocumentCopyDAO5] Lỗi truy vấn getCopyByBarcode5: " + e.getMessage());
            }
        }

        // Fallback In-Memory
        for (DocumentCopy5 c : MockDatabase5.getInstance().documentCopies) {
            if (c.getBarcode().equalsIgnoreCase(trimmed)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Tìm bản sao theo mã vạch VÀ xác nhận trạng thái 'Có sẵn' để mượn.
     * Phục vụ Module 2: Thêm cuốn sách vào danh sách mượn.
     */
    public DocumentCopy5 getAvailableCopyByBarcode5(String barcode) {
        DocumentCopy5 copy = getCopyByBarcode5(barcode);
        if (copy != null && copy.isAvailable()) {
            return copy;
        }
        return null;
    }

    /**
     * Cập nhật trạng thái bản sao (Ví dụ chuyển sang 'Đang mượn' hoặc 'Có sẵn').
     */
    public boolean updateCopyStatus5(int copyId, String newStatus) {
        ensureConnection();
        if (this.conn != null) {
            String sql = "UPDATE tblDocumentCopy5 SET status = ? WHERE id = ?";
            try (PreparedStatement ps = this.conn.prepareStatement(sql)) {
                ps.setString(1, newStatus);
                ps.setInt(2, copyId);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("[DocumentCopyDAO5] Lỗi cập nhật updateCopyStatus5: " + e.getMessage());
            }
        }

        // Fallback In-Memory
        for (DocumentCopy5 c : MockDatabase5.getInstance().documentCopies) {
            if (c.getId() == copyId) {
                c.setStatus(newStatus);
                return true;
            }
        }
        return false;
    }

    private void ensureConnection() {
        if (this.conn == null) {
            this.conn = getConnection();
        }
    }
}

