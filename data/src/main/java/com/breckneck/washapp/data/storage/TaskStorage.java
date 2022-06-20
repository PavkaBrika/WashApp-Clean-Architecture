package com.breckneck.washapp.data.storage;

import com.breckneck.washapp.data.storage.entity.Task;

import java.util.List;

public interface TaskStorage {

    List<Task> getAllTasks(long id);

    void insertTask(Task task);

    void deleteTask(Task task);

    void updateTask(long id, int frequency);

    Task checkTask(long id);

    int getTaskFrequency(long id);

    void substractFrequency();

    List<Task> getNullFrequencyTasks();
}
