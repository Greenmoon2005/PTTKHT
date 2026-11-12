package vn.edu.ptit.libman.util;

import vn.edu.ptit.libman.model.Reader5;
import vn.edu.ptit.libman.model.Staff5;
import vn.edu.ptit.libman.model.User5;

/**
 * Quản lý phiên làm việc SessionManager5 của người dùng hiện tại trên Kiosk / Desktop.
 * So khớp với Sơ đồ gói (Package Diagram - Hậu tố 5 - B23DCCN015).
 */
public class SessionManager5 {
    private static User5 currentUser5;

    public static User5 getCurrentUser5() {
        return currentUser5;
    }

    public static void setCurrentUser5(User5 user) {
        currentUser5 = user;
    }

    public static Reader5 getCurrentReader5() {
        if (currentUser5 instanceof Reader5) {
            return (Reader5) currentUser5;
        }
        return null;
    }

    public static Staff5 getCurrentStaff5() {
        if (currentUser5 instanceof Staff5) {
            return (Staff5) currentUser5;
        }
        return null;
    }

    public static void clearSession() {
        currentUser5 = null;
    }
}

