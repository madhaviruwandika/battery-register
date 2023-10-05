package com.powerledger.batterysyncservice.model;
public class Battery {
    private String name;
    private String capacity;

    public Battery() {
    }

    public Battery(String name, String capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
