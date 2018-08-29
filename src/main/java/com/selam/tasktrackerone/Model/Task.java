package com.selam.tasktrackerone.Model;

public class Task {
    private int id;
    private String name;
    private String frequency;
    private String description;

    public Task() {

    }

    public Task(int id, String name, String frequency, String description) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
        this.description = description;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
