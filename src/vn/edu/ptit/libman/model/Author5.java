package vn.edu.ptit.libman.model;

import java.io.Serializable;

/**
 * Thực thể Author5 - Tác giả tài liệu.
 * So khớp hoàn toàn với Sơ đồ lớp Thực thể (Hậu tố 5 - B23DCCN015).
 */
public class Author5 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String authorCode;
    private String fullName;
    private String biography;
    private String nationality;

    public Author5() {
    }

    public Author5(int id, String authorCode, String fullName, String biography, String nationality) {
        this.id = id;
        this.authorCode = authorCode;
        this.fullName = fullName;
        this.biography = biography;
        this.nationality = nationality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(String authorCode) {
        this.authorCode = authorCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return fullName != null ? fullName : "";
    }
}

