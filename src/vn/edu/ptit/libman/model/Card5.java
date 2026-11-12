package vn.edu.ptit.libman.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Thực thể Card5 - Thẻ thư viện của bạn đọc.
 * So khớp hoàn toàn với thiết kế hệ thống (Hậu tố 5 - B23DCCN015).
 */
public class Card5 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String cardCode;
    private int readerId;
    private Date issueDate;
    private Date expireDate;
    private String status;
    private String barcode;

    public Card5() {
    }

    public Card5(int id, String cardCode, int readerId, Date issueDate, Date expireDate, String status, String barcode) {
        this.id = id;
        this.cardCode = cardCode;
        this.readerId = readerId;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.status = status;
        this.barcode = barcode;
    }

    public boolean isValid() {
        if (!"Active".equalsIgnoreCase(status) && !"Hoạt động".equalsIgnoreCase(status)) {
            return false;
        }
        if (expireDate != null && expireDate.before(new Date())) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}

