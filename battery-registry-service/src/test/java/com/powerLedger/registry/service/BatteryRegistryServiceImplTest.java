package com.powerLedger.registry.service;

import com.powerLedger.registry.dao.BatteryRepository;
import com.powerLedger.registry.dao.Entity.Battery;
import com.powerLedger.registry.model.BatteryDTO;
import com.powerLedger.registry.model.RegistryResponseWrapper;
import com.powerLedger.registry.service.BatteryRegistryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BatteryRegistryServiceImplTest {
    BatteryRegistryServiceImpl batteryRegistryService;

    @MockBean
    BatteryRepository batteryRepository;

    @BeforeEach
    public void setup(){
        batteryRegistryService = new BatteryRegistryServiceImpl();
        batteryRegistryService.setBatteryRepository(batteryRepository);
    }

    @Test
    public void testBatteryRegistrationSuccessWithOneRecord() {
        // data creation
        Battery test = new Battery(1, "test", 1001, 50, new Date(), null );
        when(batteryRepository.saveAll(any())).thenReturn(List.of(test));
        BatteryDTO batteryDTO = new BatteryDTO( "test", 1001, 50);

        // execution
        RegistryResponseWrapper responseWrapper = batteryRegistryService.registerBatteries(List.of(batteryDTO));

        // Assertion
        assertEquals("Success", responseWrapper.getData().get("status") );
        assertNull(responseWrapper.getErrorMessages());
    }

    @Test
    public void testBatteryRegistrationSuccessWithtwoRecord() {
        Battery test1 = new Battery(1, "test", 1001, 50, new Date(), null );
        Battery test2 = new Battery(2, "test2", 1001, 50, new Date(), null );
        when(batteryRepository.saveAll(any())).thenReturn(List.of(test1,test2));
        BatteryDTO batteryDTO1 = new BatteryDTO( "test1", 1001, 50);
        BatteryDTO batteryDTO2 = new BatteryDTO( "test2", 1001, 50);

        RegistryResponseWrapper responseWrapper = batteryRegistryService.registerBatteries(List.of(batteryDTO1,batteryDTO2));

        assertEquals("Success", responseWrapper.getData().get("status") );
        assertNull(responseWrapper.getErrorMessages());
    }

    @Test
    public void testBatteryRegistrationWithDuplicateRecords() {
        when(batteryRepository.saveAll(any())).thenThrow(DataIntegrityViolationException.class);
        BatteryDTO batteryDTO1 = new BatteryDTO( "test1", 1001, 50);
        BatteryDTO batteryDTO2 = new BatteryDTO( "test", 1001, 50);

        RegistryResponseWrapper responseWrapper = batteryRegistryService.registerBatteries(List.of(batteryDTO1,batteryDTO2));

        assertEquals("Error", responseWrapper.getData().get("status") );
        assertEquals(1, responseWrapper.getErrorMessages().size());
    }

    @Test
    public void testBatteryRegistrationWithDBConnectionException() {
        when(batteryRepository.saveAll(any())).thenThrow(HttpClientErrorException.class);
        BatteryDTO batteryDTO1 = new BatteryDTO( "test1", 1001, 50);
        BatteryDTO batteryDTO2 = new BatteryDTO( "test", 1001, 50);

        RegistryResponseWrapper responseWrapper = batteryRegistryService.registerBatteries(List.of(batteryDTO1,batteryDTO2));

        assertEquals("Error", responseWrapper.getData().get("status") );
        assertEquals(1, responseWrapper.getErrorMessages().size());
    }
}
