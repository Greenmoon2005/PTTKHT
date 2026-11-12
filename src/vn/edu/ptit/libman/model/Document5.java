package vn.edu.ptit.libman.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp thực thể Document5 - Đầu mục tài liệu / Tựa sách trong thư viện.
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 1 & Module 2 (Hậu tố 5 - B23DCCN015).
 */
public class Document5 implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String docCode;
    protected String title;
    protected int publishYear;
    protected String language;
    protected double price;
    protected String locationShelf;
    protected String summary;
    protected Category5 category5;
    protected Publisher5 publisher5;
    protected List<Author5> authors5;
    protected List<DocumentCopy5> copies5;

    public Document5() {
        this.authors5 = new ArrayList<>();
        this.copies5 = new ArrayList<>();
        this.language = "Tiếng Việt";
    }

    public Document5(int id, String docCode, String title, int publishYear, String language, 
                     double price, String locationShelf, String summary, Category5 category5, 
                     Publisher5 publisher5) {
        this.id = id;
        this.docCode = docCode;
        this.title = title;
        this.publishYear = publishYear;
        this.language = language;
        this.price = price;
        this.locationShelf = locationShelf;
        this.summary = summary;
        this.category5 = category5;
        this.publisher5 = publisher5;
        this.authors5 = new ArrayList<>();
        this.copies5 = new ArrayList<>();
    }

    public String getAuthorNames() {
        if (authors5 == null || authors5.isEmpty()) {
            return "Nhiều tác giả";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < authors5.size(); i++) {
            sb.append(authors5.get(i).getFullName());
            if (i < authors5.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public int getAvailableCopiesCount() {
        if (copies5 == null) return 0;
        int count = 0;
        for (DocumentCopy5 copy : copies5) {
            if (copy.isAvailable()) count++;
        }
        return count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocationShelf() {
        return locationShelf;
    }

    public void setLocationShelf(String locationShelf) {
        this.locationShelf = locationShelf;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Category5 getCategory5() {
        return category5;
    }

    public void setCategory5(Category5 category5) {
        this.category5 = category5;
    }

    public Publisher5 getPublisher5() {
        return publisher5;
    }

    public void setPublisher5(Publisher5 publisher5) {
        this.publisher5 = publisher5;
    }

    public List<Author5> getAuthors5() {
        return authors5;
    }

    public void setAuthors5(List<Author5> authors5) {
        this.authors5 = authors5;
    }

    public List<DocumentCopy5> getCopies5() {
        return copies5;
    }

    public void setCopies5(List<DocumentCopy5> copies5) {
        this.copies5 = copies5;
    }
}

