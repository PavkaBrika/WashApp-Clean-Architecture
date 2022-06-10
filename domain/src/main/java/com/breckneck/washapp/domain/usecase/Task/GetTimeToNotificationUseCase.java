package com.breckneck.washapp.domain.usecase.Task;

import com.breckneck.washapp.domain.repository.TaskRepository;

public class GetTimeToNotificationUseCase {

    TaskRepository taskRepository;

    public GetTimeToNotificationUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public int execute(long id) {
        return taskRepository.getTaskFrequency(id);
    }
}
