package com.breckneck.washapp.domain.usecase.Task;

import com.breckneck.washapp.domain.repository.TaskRepository;

public class SubstractNotificationUseCase {

    TaskRepository taskRepository;

    public SubstractNotificationUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute() {
        taskRepository.substractFrequency();
    }
}
