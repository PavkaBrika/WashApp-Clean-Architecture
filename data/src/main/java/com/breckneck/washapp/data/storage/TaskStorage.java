package com.breckneck.washapp.data.storage;

import com.breckneck.washapp.data.storage.entity.Task;

import java.util.List;

public interface TaskStorage {

    public List<Task> getAllTasks(long id);

    public void insertTask(Task task);

    public void deleteTask(Task task);

    public void updateTask(long id, int frequency);

    public Task checkTask(long id);

    public int getTaskFrequency(long id);
}
