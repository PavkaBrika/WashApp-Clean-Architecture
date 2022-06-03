package com.breckneck.washapp.domain.usecase.Zone;

import com.breckneck.washapp.domain.model.ZoneApp;
import com.breckneck.washapp.domain.repository.ZoneRepository;

public class AddZoneUseCase {

    ZoneRepository zoneRepository;

    public AddZoneUseCase(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public void execute(String name) {
        zoneRepository.insertZone(name);
    }
}
