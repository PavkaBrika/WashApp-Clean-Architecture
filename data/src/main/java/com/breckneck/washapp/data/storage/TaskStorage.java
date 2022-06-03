package com.breckneck.washapp.data.storage;

import com.breckneck.washapp.data.storage.entity.Task;

import java.util.List;

public interface TaskStorage {

    public List<Task> getAllTasks(long id);

    public void insertTask(Task task);

    public void deleteTask(Task task);
}
