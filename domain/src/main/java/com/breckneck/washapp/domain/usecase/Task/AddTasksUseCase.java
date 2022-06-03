package com.breckneck.washapp.domain.usecase.Task;

import com.breckneck.washapp.domain.repository.TaskRepository;

public class AddTasksUseCase {

    TaskRepository taskRepository;

    public AddTasksUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(long id, String name) {
        taskRepository.insertTask(id, name);
    }
}
