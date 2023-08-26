package com.powerLedger.registry.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class RegistryResponseWrapper {
    Map<String, String> data;
    List<ErrorMessage> errorMessages;

}
