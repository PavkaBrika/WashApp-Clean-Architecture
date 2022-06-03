package com.breckneck.washapp.data.repository;

import com.breckneck.washapp.data.storage.TaskStorage;
import com.breckneck.washapp.data.storage.entity.Task;
import com.breckneck.washapp.domain.model.TaskApp;
import com.breckneck.washapp.domain.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {

    TaskStorage taskStorage;

    public TaskRepositoryImpl(TaskStorage taskStorage) {
        this.taskStorage = taskStorage;
    }

    @Override
    public List<TaskApp> getAllTasks(long id) {
        List<Task> tasksList = taskStorage.getAllTasks(id);
        List<TaskApp> tasksAppList = tasksList.stream().map(task -> new TaskApp(task.id, task.zoneId, task.taskName)).collect(Collectors.toList());
        return tasksAppList;
    }

    @Override
    public void insertTask(long id,String name) {
        Task task = new Task();
        task.zoneId = id;
        task.taskName = name;
        taskStorage.insertTask(task);
    }

    @Override
    public void deleteTask(long id) {
        Task task = new Task();
        task.id = id;
        taskStorage.deleteTask(task);
    }
}
