package com.powerLedger.registry.service;

import com.powerLedger.registry.model.BatteryDTO;
import com.powerLedger.registry.model.RegistryResponseWrapper;

import java.util.List;
public interface BatteryRegistryService {
    public RegistryResponseWrapper registerBatteries(List<BatteryDTO> batteryDTOS);
}
