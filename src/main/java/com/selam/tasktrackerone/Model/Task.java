package com.selam.tasktrackerone.Model;

import java.time.LocalTime;

public class Task {
    private int id;
    private String name;
    private String frequencyDescription;
    private String description;
    private LocalTime nextDeadline;
    private LocalTime lastDone;

    public Task(int id, String name, String frequencyDescription, String description, LocalTime nextDeadline, LocalTime lastDone) {
        this.id = id;
        this.name = name;
        this.frequencyDescription = frequencyDescription;
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

    public String getFrequencyDescription() {
        return frequencyDescription;
    }

    public void setFrequencyDescription(String frequencyDescription) {
        this.frequencyDescription = frequencyDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
