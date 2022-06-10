package com.breckneck.washapp.domain.model;

public class TaskApp {

    public long id;
    public long zoneId;
    public String taskName;
    public int frequency;

    public TaskApp(long id, long zoneId, String taskName) {
        this.id = id;
        this.zoneId = zoneId;
        this.taskName = taskName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getZoneId() {
        return zoneId;
    }

    public void setZoneId(long zoneId) {
        this.zoneId = zoneId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
