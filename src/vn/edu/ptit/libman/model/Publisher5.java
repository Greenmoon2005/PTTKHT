package vn.edu.ptit.libman.model;

import java.io.Serializable;

/**
 * Thực thể Publisher5 - Nhà xuất bản tài liệu.
 * So khớp hoàn toàn với Sơ đồ lớp Thực thể (Hậu tố 5 - B23DCCN015).
 */
public class Publisher5 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String publisherCode;
    private String name;
    private String address;
    private String phone;
    private String email;

    public Publisher5() {
    }

    public Publisher5(int id, String publisherCode, String name, String address, String phone, String email) {
        this.id = id;
        this.publisherCode = publisherCode;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublisherCode() {
        return publisherCode;
    }

    public void setPublisherCode(String publisherCode) {
        this.publisherCode = publisherCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name != null ? name : "";
    }
}

