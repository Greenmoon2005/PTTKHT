package vn.edu.ptit.libman.model;

import java.util.Date;

/**
 * Thực thể Reader5 - Bạn đọc / Sinh viên mượn sách.
 * Kế thừa User5, sở hữu quan hệ 1-1 với Card5.
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết và CSDL (Hậu tố 5 - B23DCCN015).
 */
public class Reader5 extends User5 {
    private static final long serialVersionUID = 1L;

    private String readerCode;
    private String faculty;
    private String studentClass;
    private Date expiryDate;
    private int maxBorrowLimit;
    private boolean active;
    private Card5 card5;

    public Reader5() {
        super();
        this.maxBorrowLimit = 5;
        this.active = true;
    }

    public Reader5(int id, String username, String password, String fullName, Date dateOfBirth, 
                   String gender, String email, String phone, String address, String role,
                   String readerCode, String faculty, String studentClass, Date expiryDate, 
                   int maxBorrowLimit, boolean active, Card5 card5) {
        super(id, username, password, fullName, dateOfBirth, gender, email, phone, address, role);
        this.readerCode = readerCode;
        this.faculty = faculty;
        this.studentClass = studentClass;
        this.expiryDate = expiryDate;
        this.maxBorrowLimit = maxBorrowLimit;
        this.active = active;
        this.card5 = card5;
    }

    public String getReaderCode() {
        return readerCode;
    }

    public void setReaderCode(String readerCode) {
        this.readerCode = readerCode;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getMaxBorrowLimit() {
        return maxBorrowLimit;
    }

    public void setMaxBorrowLimit(int maxBorrowLimit) {
        this.maxBorrowLimit = maxBorrowLimit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Card5 getCard5() {
        return card5;
    }

    public void setCard5(Card5 card5) {
        this.card5 = card5;
    }
}

