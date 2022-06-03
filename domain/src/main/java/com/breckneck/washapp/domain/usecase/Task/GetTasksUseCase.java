package com.breckneck.washapp.domain.usecase.Task;

import com.breckneck.washapp.domain.model.TaskApp;
import com.breckneck.washapp.domain.repository.TaskRepository;

import java.util.List;

public class GetTasksUseCase {

    TaskRepository taskRepository;

    public GetTasksUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskApp> execute(long id) {
        return taskRepository.getAllTasks(id);
    }
}
