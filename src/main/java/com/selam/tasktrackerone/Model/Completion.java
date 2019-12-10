package com.selam.tasktrackerone.Model;

import java.time.LocalDateTime;

public class Completion {
    int id;
    LocalDateTime time;
    String comment;
    String employeeUsername;
    int taskId;
    LocalDateTime taskDeadline;

    public Completion(int id, LocalDateTime time, String comment, String employeeUsername, int taskId, LocalDateTime taskDeadline) {
        this.id = id;
        this.time = time;
        this.comment = comment;
        this.employeeUsername = employeeUsername;
        this.taskId = taskId;
        this.taskDeadline=taskDeadline;
    }

    public Completion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(LocalDateTime taskDeadline) {
        this.taskDeadline = taskDeadline;
    }
}
