package com.selam.tasktrackerone.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

public class Task {
    private int id;
    private String name;
    private String description;
    private int type;
    private LocalDateTime nextDeadline;
    private LocalDateTime lastDone;
    private int sequence;

    public Task(){
    }
    public Task(int id, String name, String description, int type, LocalDateTime nextDeadline, LocalDateTime lastDone, int sequence) {
        this.id = id;
        this.name = name;
        this.type= type;
        this.description = description;
        this.nextDeadline = nextDeadline;
        this.lastDone = lastDone;
        this.sequence=sequence;
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

    public LocalDateTime getNextDeadline() {
        return nextDeadline;
    }

    public void setNextDeadline(LocalDateTime nextDeadline) {
        this.nextDeadline = nextDeadline;
    }

    public LocalDateTime getLastDone() { return lastDone; }

    public void setLastDone(LocalDateTime lastDone) {
        this.lastDone = lastDone;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

}
