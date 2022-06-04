package com.breckneck.washapp.domain.usecase.Task;

import com.breckneck.washapp.domain.model.TaskApp;
import com.breckneck.washapp.domain.repository.TaskRepository;

public class SetFrequencyUseCase {

    TaskRepository taskRepository;

    public SetFrequencyUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(long id, int positionFrequency, String customFrequency, boolean myvariant) {
        int frequency = 0;
        if (myvariant) {
            frequency = Integer.parseInt(customFrequency);
        }
        switch (positionFrequency) {
            case 1:
                frequency = 1;
                break;
            case 2:
                frequency = 2;
                break;
            case 3:
                frequency = 3;
                break;
            case 4:
                frequency = 4;
                break;
            case 5:
                frequency = 5;
                break;
            case 6:
                frequency = 6;
                break;
            case 7:
                frequency = 7;
                break;
            case 8:
                frequency = 14;
                break;
            case 9:
                frequency = 21;
                break;
            case 10:
                frequency = 30;
                break;
            case 11:
                frequency = 60;
                break;
            case 12:
                frequency = 90;
                break;
        }
        taskRepository.updateTask(id, frequency);
    }


}
