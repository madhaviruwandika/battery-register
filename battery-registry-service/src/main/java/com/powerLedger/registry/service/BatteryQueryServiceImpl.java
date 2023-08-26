package com.powerLedger.registry.service;

import com.powerLedger.registry.dao.BatteryRepository;
import com.powerLedger.registry.dao.Entity.Battery;
import com.powerLedger.registry.model.BatteryFilterDTO;
import com.powerLedger.registry.model.QueryResponseWrapper;
import com.powerLedger.registry.model.RegistryResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BatteryQueryServiceImpl implements BatteryQueryService {
    BatteryRepository batteryRepository;

    @Autowired
    public void setBatteryRepository(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }


    /**
     * Return filtered battery list which was in the given post code range
     * List will be sorted based on the given criteria
     */
    @Override
    public QueryResponseWrapper getFilteredBatteriesByPostCode(BatteryFilterDTO batteryFilterDTO) {
        log.info("Querying batteries. Filter Criteria - {}" , batteryFilterDTO.toString());
        Sort sort = Sort.by(batteryFilterDTO.getSortOrder().getDirection(), batteryFilterDTO.getSortBy().getColumn());
        List<Battery> batteryList = batteryRepository.findBatteriesByPostCodeBetween(
                batteryFilterDTO.getPostCodeFrom(), batteryFilterDTO.getPostCodeTo(), sort);
        log.info("Querying batteries. Result set - {}" , batteryList.toString());
        return buildResponse(batteryList);
    }

    /**
     * Return filtered battery list which has lower watt capacity than the given value
     */
    @Override
    public QueryResponseWrapper getFilteredBatteriesByWattCapacity(int maxWattCapacity) {
        log.info("Querying batteries. max watt capacity - {} ", maxWattCapacity);
        List<Battery> batteryList = batteryRepository.findBatteriesBelowWattCapacity(maxWattCapacity);
        log.info("Querying batteries. Result - {} ", batteryList.toString());

        return buildResponse(batteryList);
    }


    /**
     * Build response with meta data
     */
    private QueryResponseWrapper buildResponse(List<Battery> batteryList) {
        QueryResponseWrapper responseWrapper = QueryResponseWrapper.builder()
                .data(batteryList)
                .errorMessage(null)
                .build();
        responseWrapper.buildMeta();

        return responseWrapper;
    }
}
