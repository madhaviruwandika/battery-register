package com.powerLedger.registry.model;

import com.powerLedger.registry.dao.Entity.Battery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class QueryResponseWrapperTest {

    @Test
    public void testMetaValueGenerationWhenThereIsNoData() {
        QueryResponseWrapper queryResponseWrapper = QueryResponseWrapper.builder().build();
        queryResponseWrapper.buildMeta();
        assertEquals(0, queryResponseWrapper.getMeta().size());
    }

    @Test
    public void testMetaValueGenerationWhenThereIsData() {
        Battery battery1 = new Battery(1,"test1", 1001,100, new Date(), null);
        Battery battery2 = new Battery(2,"test2", 1002,50, new Date(), null);
        Battery battery3 = new Battery(3,"test3", 1003,350, new Date(), null);
        QueryResponseWrapper queryResponseWrapper = QueryResponseWrapper.builder().data(List.of(battery1,battery2,battery3)).build();
        queryResponseWrapper.buildMeta();
        assertEquals(3, queryResponseWrapper.getMeta().size());
        assertEquals("3", queryResponseWrapper.getMeta().get("total_battery_count"));
        assertEquals("500", queryResponseWrapper.getMeta().get("total_watt_capacity"));
        assertEquals("166", queryResponseWrapper.getMeta().get("avg_watt_capacity"));
    }
}
