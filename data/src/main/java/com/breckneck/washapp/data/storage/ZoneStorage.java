package com.breckneck.washapp.data.storage;

import com.breckneck.washapp.data.storage.entity.Zone;

import java.util.List;

public interface ZoneStorage {

    List<Zone> getAllZones();

    void insertZone(Zone zone);

    void deleteZone(Zone zone);
}
