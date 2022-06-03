package com.breckneck.washapp.data.repository;

import com.breckneck.washapp.data.storage.ZoneStorage;
import com.breckneck.washapp.data.storage.entity.Zone;
import com.breckneck.washapp.domain.model.ZoneApp;
import com.breckneck.washapp.domain.repository.ZoneRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ZoneRepositoryImpl implements ZoneRepository {

    ZoneStorage zoneStorage;

    public ZoneRepositoryImpl(ZoneStorage zoneStorage) {
        this.zoneStorage = zoneStorage;
    }

    @Override
    public List<ZoneApp> getAllZones() {
        List<Zone> zonesList = zoneStorage.getAllZones();
        List<ZoneApp> zoneAppList = zonesList.stream().map(zone -> new ZoneApp(zone.id, zone.zoneName, zone.picture)).collect(Collectors.toList());
        return zoneAppList;
    }

    @Override
    public void insertZone(String name) {
        Zone zone = new Zone();
        zone.zoneName = name;
        zoneStorage.insertZone(zone);
    }

    @Override
    public void deleteZone(long id) {
        Zone zone = new Zone();
        zone.id = id;
        zoneStorage.deleteZone(zone);
    }
}
