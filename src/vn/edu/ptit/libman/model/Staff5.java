package vn.edu.ptit.libman.model;

import java.util.Date;

/**
 * Thực thể Staff5 - Thủ thư tác nghiệp cho bạn đọc mượn tài liệu.
 * Kế thừa User5. So khớp với Sơ đồ lớp chi tiết và CSDL (Hậu tố 5 - B23DCCN015).
 */
public class Staff5 extends User5 {
    private static final long serialVersionUID = 1L;

    private String staffCode;
    private String position;
    private String department;
    private double salary;

    public Staff5() {
        super();
    }

    public Staff5(int id, String username, String password, String fullName, Date dateOfBirth, 
                  String gender, String email, String phone, String address, String role,
                  String staffCode, String position, String department, double salary) {
        super(id, username, password, fullName, dateOfBirth, gender, email, phone, address, role);
        this.staffCode = staffCode;
        this.position = position;
        this.department = department;
        this.salary = salary;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}

