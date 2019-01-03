package com.selam.tasktrackerone.Model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalTask extends Task {
     List<LocalTime> deadlines = new ArrayList<>();
     LocalTime nextDeadline; //from database, deadline that doesnt have a completion (?)
     LocalTime lastDone;

    public PeriodicalTask(List<LocalTime> deadlines, LocalTime nextDeadline, LocalTime lastDone) {
        this.deadlines = deadlines;
        this.nextDeadline = nextDeadline;
        this.lastDone = lastDone;
    }

    public PeriodicalTask(int id, String name, String frequencyDescription, String description, List<LocalTime> deadlines, LocalTime nextDeadline, LocalTime lastDone) {
        super(id, name, frequencyDescription, description);
        this.deadlines = deadlines;
        this.nextDeadline = nextDeadline;
        this.lastDone = lastDone;
    }

    public List<LocalTime> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<LocalTime> deadlines) {
        this.deadlines = deadlines;
    }

    public LocalTime getNextDeadline() {
        return nextDeadline;
    }

    public void setNextDeadline(LocalTime nextDeadline) {
        this.nextDeadline = nextDeadline;
    }

    public LocalTime getLastDone() {
        return lastDone;
    }

    public void setLastDone(LocalTime lastDone) {
        this.lastDone = lastDone;
    }
}
