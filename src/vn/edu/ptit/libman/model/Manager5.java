package vn.edu.ptit.libman.model;

import java.util.Date;

/**
 * Thực thể Manager5 - Người quản lý thư viện.
 * Kế thừa User5. So khớp với Sơ đồ lớp Thực thể (Hậu tố 5 - B23DCCN015).
 */
public class Manager5 extends User5 {
    private static final long serialVersionUID = 1L;

    private String managementLevel;
    private String officeRoom;

    public Manager5() {
        super();
    }

    public Manager5(int id, String username, String password, String fullName, Date dateOfBirth, 
                    String gender, String email, String phone, String address, String role,
                    String managementLevel, String officeRoom) {
        super(id, username, password, fullName, dateOfBirth, gender, email, phone, address, role);
        this.managementLevel = managementLevel;
        this.officeRoom = officeRoom;
    }

    public String getManagementLevel() {
        return managementLevel;
    }

    public void setManagementLevel(String managementLevel) {
        this.managementLevel = managementLevel;
    }

    public String getOfficeRoom() {
        return officeRoom;
    }

    public void setOfficeRoom(String officeRoom) {
        this.officeRoom = officeRoom;
    }
}

