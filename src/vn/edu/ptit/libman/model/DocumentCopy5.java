package vn.edu.ptit.libman.model;

import java.io.Serializable;

/**
 * Thực thể DocumentCopy5 - Bản sao tài liệu vật lý (cuốn sách cụ thể trong kho).
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 1 & Module 2 (Hậu tố 5 - B23DCCN015).
 */
public class DocumentCopy5 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String barcode;
    private int documentId;
    private int copyNumber;
    private int printYear;
    private String status;           // 'Có sẵn', 'Đang mượn', 'Bảo trì'
    private String conditionStatus;  // 'Mới 99%', 'Tốt', 'Sờn gáy'
    private String locationRack;     // Vị trí giá/ngăn sách
    private Document5 document5;     // Tài liệu đầu mục mà bản sao này trực thuộc

    public DocumentCopy5() {
        this.status = "Có sẵn";
        this.conditionStatus = "Mới 100%";
    }

    public DocumentCopy5(int id, String barcode, int documentId, int copyNumber, int printYear, 
                         String status, String conditionStatus, String locationRack, Document5 document5) {
        this.id = id;
        this.barcode = barcode;
        this.documentId = documentId;
        this.copyNumber = copyNumber;
        this.printYear = printYear;
        this.status = status;
        this.conditionStatus = conditionStatus;
        this.locationRack = locationRack;
        this.document5 = document5;
    }

    public boolean isAvailable() {
        return "Có sẵn".equalsIgnoreCase(status) || "Available".equalsIgnoreCase(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

    public void setCopyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
    }

    public int getPrintYear() {
        return printYear;
    }

    public void setPrintYear(int printYear) {
        this.printYear = printYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConditionStatus() {
        return conditionStatus;
    }

    public void setConditionStatus(String conditionStatus) {
        this.conditionStatus = conditionStatus;
    }

    public String getCondition() {
        return conditionStatus;
    }

    public void setCondition(String condition) {
        this.conditionStatus = condition;
    }

    public String getLocationRack() {
        return locationRack;
    }

    public void setLocationRack(String locationRack) {
        this.locationRack = locationRack;
    }

    public Document5 getDocument5() {
        return document5;
    }

    public void setDocument5(Document5 document5) {
        this.document5 = document5;
    }
}

