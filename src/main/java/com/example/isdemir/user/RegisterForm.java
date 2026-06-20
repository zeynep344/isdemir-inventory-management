package com.example.isdemir.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterForm {

    @NotBlank
    @Size(max = 255)
    private String username;

    // password -> pass
    @NotBlank
    @Size(min = 6, max = 100)
    private String pass;

    // Opsiyonel bıraktık; formda alan yoksa validasyon patlamasın
    @Size(min = 6, max = 100)
    private String confirmPassword;

    // Formda ilk seçenek value="" olduğu için zorunluluğu kaldırdık.
    // Serviste boşsa "OPERATOR" atayabilirsin.
    @Size(max = 255)
    private String permission;

    @Size(max = 255)
    private String fullname;

    @Size(max = 255)
    private String department;

    public RegisterForm() {}

    // ------- GETTERS & SETTERS -------
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
