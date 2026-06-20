package com.example.isdemir.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_data") // id, username, pass, permission, fullname, department
public class User {

    public enum Role {
        ADMIN,
        OPERATOR,
        USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String username;

    // DB sütunu "pass" → BCrypt hash saklanacak
    @Column(name = "pass", nullable = false, length = 60)
    private String pass;

    // DB sütunu "permission"
    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false, length = 50)
    private Role permission;

    // DB sütunu "fullname"
    @Column(name = "fullname", length = 255)
    private String fullname;

    // DB sütunu "department"
    @Column(name = "department", length = 255)
    private String department;

    public User() {
    }

    // ------- GETTERS & SETTERS -------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Role getPermission() {
        return permission;
    }

    public void setPermission(Role permission) {
        this.permission = permission;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}