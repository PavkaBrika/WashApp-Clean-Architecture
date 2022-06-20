package com.breckneck.washapp.data.storage.database;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.breckneck.washapp.data.storage.TaskStorage;
import com.breckneck.washapp.data.storage.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class DataBaseTaskStorageImpl implements TaskStorage {

    private final String SHARED_PREFS_NAME_2 = "shared_prefs_name_2";
    private final String TASK_ID = "taskid";

    Context context;
    AppDataBase db;
    List<Task> tasksList = new ArrayList<>();
    SharedPreferences sharedPreferences;

    public DataBaseTaskStorageImpl(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context, AppDataBase.class, "ZoneDataBase").build();
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME_2, Context.MODE_PRIVATE);
    }


    @Override
    public List<Task> getAllTasks(long id) {
        tasksList  = db.zoneDao().getTasksByZoneId(id);
        return tasksList;
    }

    @Override
    public void insertTask(Task task) {
        long taskid = sharedPreferences.getLong(TASK_ID, 0);
        task.id = taskid;
        taskid++;
        db.zoneDao().insertTask(task);
        sharedPreferences.edit().putLong(TASK_ID, taskid).apply();
    }

    @Override
    public void deleteTask(Task task) {
        db.zoneDao().deleteTask(db.zoneDao().getTaskById(task.id));
    }

    @Override
    public void updateTask(long id, int frequency) {
        Task task = db.zoneDao().getTaskById(id);
        task.frequency = frequency;
        db.zoneDao().updateTask(task);
    }

    @Override
    public Task checkTask(long id) {
        Task task = db.zoneDao().getTaskById(id);
        return task;
    }

    @Override
    public int getTaskFrequency(long id) {
        Task task = db.zoneDao().getTaskById(id);
        return task.frequency;
    }

}
