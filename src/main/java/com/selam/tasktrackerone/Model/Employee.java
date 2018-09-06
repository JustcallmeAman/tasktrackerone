package com.selam.tasktrackerone.Model;

public class Employee {

    int id;
    String username;
    String encryptedPassword;
    boolean manager;
    public Employee(){

    }
    public Employee(int id, String username, String encryptedPassword, boolean manager) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.manager = manager;
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }
}
