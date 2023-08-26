package com.powerLedger.registry.model;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum SortOrder {
    ASC(Sort.Direction.ASC),
    DESC(Sort.Direction.DESC);

    private final Sort.Direction direction;
    SortOrder(Sort.Direction direction) {
        this.direction = direction;
    }
};
