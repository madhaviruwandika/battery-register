package com.powerLedger.registry.model;

import lombok.Getter;

@Getter
public enum SortBy {
    NAME("name") {
        @Override
        public String toString() {
            return "NAME";
        }
    },
    WATT_CAPACITY("watt_capacity"){
        @Override
        public String toString() {
            return "WATT_CAPACITY";
        }
    };

    private String column;

    SortBy(String column){
        this.column = column;
    }

    public String getColumn() {
        return this.column;
    }

}
