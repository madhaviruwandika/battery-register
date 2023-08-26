package com.powerLedger.registry.service;

import com.powerLedger.registry.dao.BatteryRepository;
import com.powerLedger.registry.dao.Entity.Battery;
import com.powerLedger.registry.model.BatteryDTO;
import com.powerLedger.registry.model.ErrorMessage;
import com.powerLedger.registry.model.RegistryResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class BatteryRegistryServiceImpl implements BatteryRegistryService{
    private BatteryRepository batteryRepository;

    @Autowired
    public void setBatteryRepository(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }

    @Override
    public RegistryResponseWrapper registerBatteries(List<BatteryDTO> batteryDTOS) {
        try {
            log.info("Saving battery information. data : {}", batteryDTOS.toString());
            List<Battery> batteries = batteryRepository.saveAll(buildBatteryEntities(batteryDTOS));

            log.info("Successfully saved. data : {} ", batteries.toString());
            RegistryResponseWrapper responseWrapper = RegistryResponseWrapper.builder()
                            .data(new HashMap<String, String>(){{put("status" , "Success");}})
                            .build();

            return responseWrapper;
        }
        catch (DataIntegrityViolationException e) {
            log.error("exception occurred. error : {}", e.getMessage());
            RegistryResponseWrapper responseWrapper = RegistryResponseWrapper.builder()
                    .data(new HashMap<String, String>(){{put("status" , "Error");}})
                    .errorMessages(new ArrayList<>() {{
                        add(new ErrorMessage(e.getMessage()));
                    }})
                    .build();

            return responseWrapper;
        } catch (Exception e) {
            log.error("exception occurred.error : {}", e.getMessage());
            RegistryResponseWrapper responseWrapper = RegistryResponseWrapper.builder()
                    .data(new HashMap<String, String>(){{put("status" , "Error");}})
                    .errorMessages(new ArrayList<>() {{
                        add(new ErrorMessage(e.getMessage()));
                    }})
                    .build();

            return responseWrapper;
        }
    }

    /**
     * Format list of battery DTO to Battery Entity
     * @param dtos battery dtos
     * @return List set of entities of batteries filled with data
     */
    private List<Battery> buildBatteryEntities(List<BatteryDTO> dtos) {
        Date createdAt = new Date();
        List<Battery> batteries = new ArrayList<>();

        for(BatteryDTO dto : dtos) {
            batteries.add(
                    Battery.
                            builder().
                            createdAt(createdAt).
                            name(dto.getName()).
                            postCode(dto.getPostCode()).
                            wattCapacity(dto.getWattCapacity()).
                            build());
        }

        return batteries;

    }
}
