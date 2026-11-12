package vn.edu.ptit.libman.model;

import java.io.Serializable;

/**
 * Thực thể Category5 - Thể loại tài liệu.
 * So khớp hoàn toàn với Sơ đồ lớp chi tiết Module 1 (Hậu tố 5 - B23DCCN015).
 */
public class Category5 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String categoryCode;
    private String name;
    private String description;

    public Category5() {
    }

    public Category5(int id, String categoryCode, String name, String description) {
        this.id = id;
        this.categoryCode = categoryCode;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name != null ? name : "";
    }
}

