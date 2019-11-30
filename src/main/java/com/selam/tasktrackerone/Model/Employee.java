package com.selam.tasktrackerone.Model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    Long id;
    String username;
    String encryptedPassword;
    int role;
    public Employee(){

    }
    public Employee(Long id, String username, String encryptedPassword, int role) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.role = role;
    }

    @Id
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
