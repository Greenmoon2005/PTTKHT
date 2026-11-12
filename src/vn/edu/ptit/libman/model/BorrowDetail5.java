package vn.edu.ptit.libman.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Thực thể BorrowDetail5 - Chi tiết từng cuốn sách trong phiếu mượn.
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 2 và CSDL (Hậu tố 5 - B23DCCN015).
 */
public class BorrowDetail5 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private BorrowSlip5 borrowSlip5;
    private DocumentCopy5 documentCopy5;
    private String borrowStatus;          // 'Đang mượn', 'Đã trả', 'Mất sách'
    private Date returnDate;
    private double fineAmount;
    private String conditionOnReturn;

    public BorrowDetail5() {
        this.borrowStatus = "Đang mượn";
        this.fineAmount = 0.0;
    }

    public BorrowDetail5(int id, BorrowSlip5 borrowSlip5, DocumentCopy5 documentCopy5, 
                         String borrowStatus, Date returnDate, double fineAmount, String conditionOnReturn) {
        this.id = id;
        this.borrowSlip5 = borrowSlip5;
        this.documentCopy5 = documentCopy5;
        this.borrowStatus = borrowStatus;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
        this.conditionOnReturn = conditionOnReturn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BorrowSlip5 getBorrowSlip5() {
        return borrowSlip5;
    }

    public void setBorrowSlip5(BorrowSlip5 borrowSlip5) {
        this.borrowSlip5 = borrowSlip5;
    }

    public DocumentCopy5 getDocumentCopy5() {
        return documentCopy5;
    }

    public void setDocumentCopy5(DocumentCopy5 documentCopy5) {
        this.documentCopy5 = documentCopy5;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getConditionOnReturn() {
        return conditionOnReturn;
    }

    public void setConditionOnReturn(String conditionOnReturn) {
        this.conditionOnReturn = conditionOnReturn;
    }
}

