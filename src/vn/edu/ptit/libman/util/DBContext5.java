package vn.edu.ptit.libman.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lớp DBContext5 - Cấu hình và quản lý kết nối cơ sở dữ liệu MySQL 8.0.
 * Hậu tố 5 - Sinh viên B23DCCN015.
 * So khớp hoàn toàn với Sơ đồ gói (Package Diagram) và Sơ đồ lớp chi tiết.
 */
public class DBContext5 {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "libman_db_5";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static final String DB_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME 
            + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    private static boolean mysqlAvailable = true;
    private static boolean checkedConnection = false;

    public static Connection getConnection() {
        if (!mysqlAvailable && checkedConnection) {
            return null;
        }

        try {
            Class.forName(DRIVER_CLASS);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (!checkedConnection) {
                System.out.println("[DBContext5] Kết nối MySQL Database '" + DB_NAME + "' thành công!");
                checkedConnection = true;
                mysqlAvailable = true;
            }
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("[DBContext5] Không tìm thấy MySQL Driver: " + e.getMessage());
            mysqlAvailable = false;
            checkedConnection = true;
            return null;
        } catch (SQLException e) {
            if (!checkedConnection) {
                System.out.println("[DBContext5] Lưu ý: Chưa kết nối được MySQL tại " + HOST + ":" + PORT + " (" + e.getMessage() + ")");
                System.out.println("[DBContext5] Kích hoạt chế độ In-Memory Repository (tải toàn bộ dữ liệu mẫu chuẩn theo thiết kế).");
                mysqlAvailable = false;
                checkedConnection = true;
            }
            return null;
        }
    }

    public static boolean isMysqlAvailable() {
        return mysqlAvailable;
    }

    public static void setMysqlAvailable(boolean available) {
        mysqlAvailable = available;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("[DBContext5] Lỗi đóng kết nối: " + e.getMessage());
            }
        }
    }
}

