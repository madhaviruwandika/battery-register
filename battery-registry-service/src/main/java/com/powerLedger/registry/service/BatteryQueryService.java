package com.powerLedger.registry.service;

import com.powerLedger.registry.dao.Entity.Battery;
import com.powerLedger.registry.model.BatteryFilterDTO;
import com.powerLedger.registry.model.QueryResponseWrapper;

import java.util.List;

public interface BatteryQueryService {
    public QueryResponseWrapper getFilteredBatteriesByPostCode(BatteryFilterDTO batteryFilterDTO);
    public QueryResponseWrapper getFilteredBatteriesByWattCapacity( int maxWattCapacity);
}
