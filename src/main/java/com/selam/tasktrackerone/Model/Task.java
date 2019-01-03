package com.selam.tasktrackerone.Model;

public class Task {
    private int id;
    private String name;
    private String frequencyDescription;
    private String description;

    public Task() {

    }

    public Task(int id, String name, String frequencyDescription, String description) {
        this.id = id;
        this.name = name;
        this.frequencyDescription = frequencyDescription;
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
}
