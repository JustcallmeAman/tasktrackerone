package com.selam.tasktrackerone.Model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Completion {
    int id;
    LocalDateTime time;
    String comment;
    Long employeeId;
    int taskId;
    LocalDateTime taskDeadline;

    public Completion(int id, LocalDateTime time, String comment, Long employeeId, int taskId, LocalDateTime taskDeadline) {
        this.id = id;
        this.time = time;
        this.comment = comment;
        this.employeeId = employeeId;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
