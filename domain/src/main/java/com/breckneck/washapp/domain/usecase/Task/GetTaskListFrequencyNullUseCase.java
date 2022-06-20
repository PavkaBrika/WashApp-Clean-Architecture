package com.breckneck.washapp.domain.usecase.Task;

import com.breckneck.washapp.domain.model.TaskApp;
import com.breckneck.washapp.domain.repository.TaskRepository;

import java.util.List;

public class GetTaskListFrequencyNullUseCase {

    TaskRepository taskRepository;

    public GetTaskListFrequencyNullUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskApp> execute() {
        return taskRepository.getNullFrequencyTasks();
    }
}
