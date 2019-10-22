package com.selam.tasktrackerone.Model;

import java.time.LocalTime;

public class Task {
    private int id;
    private String name;
    private String description;
    private int type;
    private LocalTime nextDeadline;
    private LocalTime lastDone;

    public Task(){
    }
    public Task(int id, String name, String description, int type, LocalTime nextDeadline, LocalTime lastDone) {
        this.id = id;
        this.name = name;
        this.type= type;
        this.description = description;
        this.nextDeadline = nextDeadline;
        this.lastDone = lastDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalTime getNextDeadline() {
        return nextDeadline;
    }

    public void setNextDeadline(LocalTime nextDeadline) {
        this.nextDeadline = nextDeadline;
    }

    public LocalTime getLastDone() { return lastDone; }

    public void setLastDone(LocalTime lastDone) {
        this.lastDone = lastDone;
    }
}
