package com.breckneck.washapp.domain.usecase.Task;

import com.breckneck.washapp.domain.repository.TaskRepository;

public class DeleteTaskUseCase {

    TaskRepository taskRepository;


    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(long id) {
        taskRepository.deleteTask(id);
    }
}
