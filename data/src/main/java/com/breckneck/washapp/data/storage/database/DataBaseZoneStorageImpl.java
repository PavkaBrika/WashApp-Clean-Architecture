package com.breckneck.washapp.data.storage.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.room.Room;

import com.breckneck.washapp.data.storage.ZoneStorage;
import com.breckneck.washapp.data.storage.entity.Zone;

import java.util.ArrayList;
import java.util.List;

public class DataBaseZoneStorageImpl implements ZoneStorage {

    private final String SHARED_PREFS_NAME = "shared_prefs_name";
    private final String ZONE_ID = "zoneid";

    Context context;
    AppDataBase db;
    List<Zone> zonesList = new ArrayList<>();
    SharedPreferences sharedPreferences;



    public DataBaseZoneStorageImpl(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context, AppDataBase.class, "ZoneDataBase").build();
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }



    @Override
    public List<Zone> getAllZones() {
        zonesList = db.zoneDao().getAllZones();
        return zonesList;
    }

    @Override
    public void insertZone(Zone zone) {
        long zoneid = sharedPreferences.getLong(ZONE_ID, 0);
        zone.id = zoneid;
        zoneid++;
        db.zoneDao().insertZone(zone);
        sharedPreferences.edit().putLong(ZONE_ID, zoneid).apply();
    }

    @Override
    public void deleteZone(Zone zone) {
        db.zoneDao().deleteZone(db.zoneDao().getZoneById(zone.id));
    }


}
