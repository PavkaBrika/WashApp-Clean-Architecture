package com.breckneck.washapp.domain.usecase.Zone;

import com.breckneck.washapp.domain.model.ZoneApp;
import com.breckneck.washapp.domain.repository.ZoneRepository;

import java.util.List;

public class GetZonesUseCase {

    ZoneRepository zoneRepository;


    public GetZonesUseCase(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public List<ZoneApp> execute() {
        return zoneRepository.getAllZones();
    }
}
