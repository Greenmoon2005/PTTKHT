package vn.edu.ptit.libman.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Lớp trừu tượng User5 đại diện cho người dùng chung trong hệ thống LibMan.
 * So khớp hoàn toàn với Sơ đồ lớp Thực thể và Sơ đồ lớp chi tiết (Hậu tố 5 - B23DCCN015).
 */
public abstract class User5 implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String username;
    protected String password;
    protected String fullName;
    protected Date dateOfBirth;
    protected String gender;
    protected String email;
    protected String phone;
    protected String address;
    protected String role;

    public User5() {
    }

    public User5(int id, String username, String password, String fullName, Date dateOfBirth, 
                 String gender, String email, String phone, String address, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

