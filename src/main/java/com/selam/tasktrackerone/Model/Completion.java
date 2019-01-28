package com.selam.tasktrackerone.Model;

import java.time.LocalTime;

public class Completion {
    int id;
    LocalTime time;
    String comment;
    Long employeeId;
    int taskId;


    public Completion(int id, LocalTime time, String comment, Long employeeId, int taskId) {
        this.id = id;
        this.time = time;
        this.comment = comment;
        this.employeeId = employeeId;
        this.taskId = taskId;
    }

    public Completion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
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
}
