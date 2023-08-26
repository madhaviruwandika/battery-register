package com.powerLedger.registry.service;

import com.powerLedger.registry.dao.BatteryRepository;
import com.powerLedger.registry.dao.Entity.Battery;
import com.powerLedger.registry.model.BatteryFilterDTO;
import com.powerLedger.registry.model.QueryResponseWrapper;
import com.powerLedger.registry.model.SortBy;
import com.powerLedger.registry.model.SortOrder;
import com.powerLedger.registry.service.BatteryQueryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BatteryQueryServiceImplTest {
    BatteryQueryServiceImpl batteryQueryServiceImpl;
    @MockBean
    BatteryRepository batteryRepository;

    @BeforeEach
    public void setup(){
        batteryQueryServiceImpl = new BatteryQueryServiceImpl();
        batteryQueryServiceImpl.setBatteryRepository(batteryRepository);
    }

    @Test
    public void testSuccessfullyRetrieveBatteryRecordsWithinPostCode() {
        Battery battery1 = new Battery(1, "test1", 1000, 30, new Date(), null);
        Battery battery2 = new Battery(1, "test2", 1001, 50, new Date(), null);
        when(batteryRepository.findBatteriesByPostCodeBetween(1,2000, Sort.by(SortOrder.ASC.getDirection(), SortBy.NAME.getColumn())))
                .thenReturn(List.of(battery1,battery2));

        BatteryFilterDTO batteryDTO = new BatteryFilterDTO(1, 2000, SortBy.NAME, SortOrder.ASC);

        QueryResponseWrapper responseWrapper = batteryQueryServiceImpl.getFilteredBatteriesByPostCode(batteryDTO);

        assertEquals(2, responseWrapper.getData().size());
        assertEquals("2", responseWrapper.getMeta().get("total_battery_count"));
    }

    @Test
    public void testExceptionWhenRetrievingBatteryRecordsWithinPostCode() {
       when(batteryRepository.findBatteriesByPostCodeBetween(1,2000, Sort.by(SortOrder.ASC.getDirection(), SortBy.NAME.getColumn())))
                .thenThrow(HttpClientErrorException.class);

        BatteryFilterDTO batteryDTO = new BatteryFilterDTO(1, 2000, SortBy.NAME, SortOrder.ASC);

        try {
            batteryQueryServiceImpl.getFilteredBatteriesByPostCode(batteryDTO);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof HttpClientErrorException);
        }
    }

}
