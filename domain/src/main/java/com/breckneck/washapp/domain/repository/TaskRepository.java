package com.breckneck.washapp.domain.repository;

import com.breckneck.washapp.domain.model.TaskApp;

import java.util.List;

public interface TaskRepository {

    List<TaskApp> getAllTasks(long id);

    void insertTask(long id, String name);

    void deleteTask(long id);
}
