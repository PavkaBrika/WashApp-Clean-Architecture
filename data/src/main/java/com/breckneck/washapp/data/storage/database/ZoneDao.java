package com.breckneck.washapp.data.storage.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.breckneck.washapp.data.storage.entity.Task;
import com.breckneck.washapp.data.storage.entity.Zone;

import java.util.List;
@Dao
public interface ZoneDao {

    @Query("SELECT * FROM zone")
    List<Zone> getAllZones();

    @Query("SELECT * FROM zone WHERE id = :id")
    Zone getZoneById(long id);

    @Query("SELECT * FROM task WHERE id = :id")
    Task getTaskById(long id);

    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task WHERE zoneId = :id")
    List<Task> getTasksByZoneId(long id);

    @Query("UPDATE task SET frequency = frequency - 1")
    void substractFrequency();

    @Query("SELECT * FROM task WHERE frequency = 0")
    List<Task> getTaskFrequencyNull();

    @Insert
    void insertZone(Zone zone);

    @Update
    void updateZone(Zone zone);

    @Delete
    void deleteZone(Zone zone);

    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);
}
