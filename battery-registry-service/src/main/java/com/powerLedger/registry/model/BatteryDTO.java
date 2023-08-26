package com.powerLedger.registry.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class BatteryDTO {

    @NotNull(message = "The name is required.")
    @Size(max = 256, message = "length of the name should be less than 256")
    private String name;

    @NotNull(message = "post code is required")
    @Pattern(regexp="[\\d]", message="Post code should contain digits!")
    private Integer postCode;

    @NotNull(message = "watt capacity is required")
    private Integer wattCapacity;
}
