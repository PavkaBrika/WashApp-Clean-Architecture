package com.breckneck.washapp.domain.usecase.Zone;

import com.breckneck.washapp.domain.repository.ZoneRepository;

public class DeleteZoneUseCase {

    ZoneRepository zoneRepository;

    public DeleteZoneUseCase(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public void execute(long id) {
        zoneRepository.deleteZone(id);
    }
}
