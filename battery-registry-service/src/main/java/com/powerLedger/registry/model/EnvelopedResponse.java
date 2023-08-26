package com.powerLedger.registry.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class EnvelopedResponse<T> {
    T data;
    List<ErrorMessage> errors;
    Map<String,String> meta;
    boolean hasError;
}
