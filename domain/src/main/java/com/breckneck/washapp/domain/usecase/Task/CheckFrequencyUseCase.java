package com.breckneck.washapp.domain.usecase.Task;

import com.breckneck.washapp.domain.model.TaskApp;
import com.breckneck.washapp.domain.repository.TaskRepository;

public class CheckFrequencyUseCase {

    TaskRepository taskRepository;

    public CheckFrequencyUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean execute(long id) {
        TaskApp taskApp = taskRepository.checkFrequency(id);
        if ((taskApp.frequency > 0) && (taskApp.frequency < 1000000)) {
            return true;
        } else {
            return false;
        }
    }
}
