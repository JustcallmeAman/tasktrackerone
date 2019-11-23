package com.selam.tasktrackerone.Model;

public class Wrapper {
    private Employee employee;
    private Completion completion;

    private Task task;

    public Wrapper(){}

    public Wrapper(Employee employee, Completion completion, Task task) {
        this.employee = employee;
        this.completion = completion;
        this.task=task;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Completion getCompletion() {
        return completion;
    }

    public void setCompletion(Completion completion) {
        this.completion = completion;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
