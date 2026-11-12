package vn.edu.ptit.libman.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Thực thể BorrowSlip5 - Phiếu cho mượn tài liệu do thủ thư lập.
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 2 và CSDL (Hậu tố 5 - B23DCCN015).
 */
public class BorrowSlip5 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String slipCode;
    private Reader5 reader5;
    private Staff5 staff5;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private String status;               // 'Đang mượn', 'Đã trả hoàn tất', 'Quá hạn'
    private double depositAmount;
    private String note;
    private List<BorrowDetail5> borrowDetails5;

    public BorrowSlip5() {
        this.borrowDate = new Date();
        this.status = "Đang mượn";
        this.depositAmount = 0.0;
        this.borrowDetails5 = new ArrayList<>();
    }

    public BorrowSlip5(int id, String slipCode, Reader5 reader5, Staff5 staff5, Date borrowDate, 
                       Date dueDate, Date returnDate, String status, double depositAmount, 
                       String note, List<BorrowDetail5> borrowDetails5) {
        this.id = id;
        this.slipCode = slipCode;
        this.reader5 = reader5;
        this.staff5 = staff5;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.depositAmount = depositAmount;
        this.note = note;
        this.borrowDetails5 = borrowDetails5 != null ? borrowDetails5 : new ArrayList<>();
    }

    public void addBorrowDetail(BorrowDetail5 detail) {
        if (this.borrowDetails5 == null) {
            this.borrowDetails5 = new ArrayList<>();
        }
        detail.setBorrowSlip5(this);
        this.borrowDetails5.add(detail);
    }

    public int getDetailCount() {
        return borrowDetails5 != null ? borrowDetails5.size() : 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public Reader5 getReader5() {
        return reader5;
    }

    public void setReader5(Reader5 reader5) {
        this.reader5 = reader5;
    }

    public Staff5 getStaff5() {
        return staff5;
    }

    public void setStaff5(Staff5 staff5) {
        this.staff5 = staff5;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<BorrowDetail5> getBorrowDetails5() {
        return borrowDetails5;
    }

    public void setBorrowDetails5(List<BorrowDetail5> borrowDetails5) {
        this.borrowDetails5 = borrowDetails5;
    }
}

