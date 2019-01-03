package com.selam.tasktrackerone.Model;

import java.time.LocalTime;

public class FrequentTask extends Task {
    Long frequency; //should be in terms of hours
    LocalTime nextDeadline;
    LocalTime lastDone;

    public FrequentTask(int id, String name, String frequencyDescription, String description, Long frequency, LocalTime nextDeadline, LocalTime lastDone) {
        super(id, name, frequencyDescription, description);
        this.frequency = frequency;
        this.nextDeadline = nextDeadline;
        this.lastDone=lastDone;
    }
    
    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    public LocalTime getNextDeadline() {
        return nextDeadline;
    }

    public void setNextDeadline(LocalTime nextDeadline) { //for setting next deadline for the very first deadline of the day
        this.nextDeadline = nextDeadline;
    }
    public LocalTime getLastDone() {
        return lastDone;
    }

    public void setLastDone(LocalTime lastDone) {
        this.lastDone = lastDone;
    }

    public void updateNextDeadline() {
       nextDeadline= lastDone.plusHours(frequency);
    }
}
