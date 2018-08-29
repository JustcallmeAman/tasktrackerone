package com.selam.tasktrackerone.Model;

public class Completion {
    int id;
    String time;
    String comment;
    int employeeId;
    int taskId;


    public Completion(int id, String time, String comment, int employeeId, int taskId) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
