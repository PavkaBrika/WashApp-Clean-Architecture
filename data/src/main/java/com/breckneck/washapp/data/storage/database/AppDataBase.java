package com.breckneck.washapp.data.storage.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.breckneck.washapp.data.storage.entity.Task;
import com.breckneck.washapp.data.storage.entity.Zone;

@Database(entities = {Zone.class, Task.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract ZoneDao zoneDao();
}
