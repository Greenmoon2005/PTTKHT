package vn.edu.ptit.libman.dao;

import java.sql.Connection;
import java.sql.SQLException;
import vn.edu.ptit.libman.util.DBContext5;

/**
 * Lớp trừu tượng DAO5 - Nền tảng cho toàn bộ các lớp Data Access Object.
 * So khớp với Sơ đồ lớp chi tiết và Giáo trình PTTKHT (Mục 5.2.1, Trang 117).
 * Hậu tố 5 - Mã SV: B23DCCN015.
 */
public abstract class DAO5 {
    public static Connection con;
    protected Connection conn;

    public DAO5() {
        if (con == null) {
            con = DBContext5.getConnection();
        }
        this.conn = con;
    }

    public Connection getConnection() {
        if (this.conn == null) {
            this.conn = DBContext5.getConnection();
            con = this.conn;
        }
        return this.conn;
    }

    public void closeConnection() {
        if (this.conn != null) {
            try {
                this.conn.close();
                this.conn = null;
                con = null;
            } catch (SQLException e) {
                System.err.println("[DAO5] Lỗi đóng kết nối: " + e.getMessage());
            }
        }
    }
}

