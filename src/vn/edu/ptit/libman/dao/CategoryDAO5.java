package vn.edu.ptit.libman.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vn.edu.ptit.libman.model.Category5;
import vn.edu.ptit.libman.util.MockDatabase5;

/**
 * Lớp CategoryDAO5 - Truy xuất dữ liệu thể loại tài liệu (tblCategory5).
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 1 (Hậu tố 5 - B23DCCN015).
 */
public class CategoryDAO5 extends DAO5 {

    public CategoryDAO5() {
        super();
    }

    /**
     * Lấy toàn bộ danh sách thể loại tài liệu để hiển thị lên ComboBox.
     */
    public List<Category5> getAllCategories5() {
        List<Category5> list = new ArrayList<>();
        ConnectionCurrent();

        if (this.conn != null) {
            String sql = "SELECT id, category_code, name, description FROM tblCategory5 ORDER BY id ASC";
            try (PreparedStatement ps = this.conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category5 cat = new Category5();
                    cat.setId(rs.getInt("id"));
                    cat.setCategoryCode(rs.getString("category_code"));
                    cat.setName(rs.getString("name"));
                    cat.setDescription(rs.getString("description"));
                    list.add(cat);
                }
                return list;
            } catch (SQLException e) {
                System.err.println("[CategoryDAO5] Lỗi truy vấn getAllCategories5: " + e.getMessage());
            }
        }

        // Fallback In-Memory
        for (Category5 c : MockDatabase5.getInstance().categories) {
            list.add(c);
        }
        return list;
    }

    /**
     * Lấy thông tin thể loại theo ID.
     */
    public Category5 getCategoryById5(int id) {
        ConnectionCurrent();
        if (this.conn != null) {
            String sql = "SELECT id, category_code, name, description FROM tblCategory5 WHERE id = ?";
            try (PreparedStatement ps = this.conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Category5 cat = new Category5();
                        cat.setId(rs.getInt("id"));
                        cat.setCategoryCode(rs.getString("category_code"));
                        cat.setName(rs.getString("name"));
                        cat.setDescription(rs.getString("description"));
                        return cat;
                    }
                }
            } catch (SQLException e) {
                System.err.println("[CategoryDAO5] Lỗi truy vấn getCategoryById5: " + e.getMessage());
            }
        }

        // Fallback In-Memory
        for (Category5 c : MockDatabase5.getInstance().categories) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    private void ConnectionCurrent() {
        if (this.conn == null) {
            this.conn = getConnection();
        }
    }
}

