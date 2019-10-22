package com.selam.tasktrackerone.Model;

import java.time.LocalTime;

public class FrequentTask extends Task {
    private Long frequency; //should be in terms of hours
    public FrequentTask(int id, String name, String description, LocalTime nextDeadline, LocalTime lastDone, Long frequency) {
        super(id, name, description, 2, nextDeadline, lastDone);
        this.frequency = frequency;
    }
    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

}
