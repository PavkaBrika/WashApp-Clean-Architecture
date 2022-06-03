package com.breckneck.washapp.domain.repository;

import com.breckneck.washapp.domain.model.ZoneApp;

import java.util.List;

public interface ZoneRepository {

    List<ZoneApp> getAllZones();

    void insertZone(String zoneName);

    void deleteZone(long id);
}
