package com.example.crudprogram;

public class Task {

    private int taskID;
    private String taskName;
    private String dueDate;
    private boolean completed;

    public Task(int taskID, String taskName, String dueDate, boolean completed) {

        this.taskID = taskID;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.completed = completed;

    }

    public int getTaskID() { return taskID; }
    public String getTaskName() { return taskName; }
    public String getDueDate() { return dueDate; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}

