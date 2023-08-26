package com.powerLedger.registry.model;

import com.powerLedger.registry.dao.Entity.Battery;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class QueryResponseWrapper {
    List<Battery> data;
    List<ErrorMessage> errorMessage;
    Map<String, String> meta;

    public void buildMeta() {
        this.meta = new HashMap<>();
        if (data != null && data.size() > 0) {
            meta.put("total_battery_count", String.valueOf(data.size()));
            meta.put("total_watt_capacity", String.valueOf(data.stream().mapToInt(b -> b.getWattCapacity()).sum()));
            meta.put("avg_watt_capacity", String.valueOf((data.stream().mapToInt(b -> b.getWattCapacity()).sum()) / data.size()));
        }
    }
}
