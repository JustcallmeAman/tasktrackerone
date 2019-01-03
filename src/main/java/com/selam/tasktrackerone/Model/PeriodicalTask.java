package com.selam.tasktrackerone.Model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalTask extends Task {
    List<LocalTime> deadlines = new ArrayList<>();

    public PeriodicalTask(int id, String name, String frequencyDescription, String description, LocalTime nextDeadline, LocalTime lastDone, List<LocalTime> deadlines) {
        super(id, name, frequencyDescription, description, nextDeadline, lastDone);
        this.deadlines = deadlines;
    }

    public List<LocalTime> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<LocalTime> deadlines) {
        this.deadlines = deadlines;
    }

}
