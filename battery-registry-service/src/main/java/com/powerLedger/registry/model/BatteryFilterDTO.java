package com.powerLedger.registry.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class BatteryFilterDTO {
    private Integer postCodeFrom;
    private Integer postCodeTo;
    private SortBy sortBy;
    private SortOrder sortOrder;


}
