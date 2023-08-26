package com.powerLedger.registry.controller;

import com.powerLedger.registry.model.BatteryDTO;
import com.powerLedger.registry.model.EnvelopedResponse;
import com.powerLedger.registry.model.RegistryResponseWrapper;
import com.powerLedger.registry.service.BatteryRegistryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller for registering battery information
 */

@RestController
@RequestMapping(value = "/api/v1/battery", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Battery Register Controller")
public class BatteryRegistryController {

    BatteryRegistryService batteryRegistryService;

    @Autowired
    public void setBatteryRegistryService(BatteryRegistryService batteryRegistryService) {
        this.batteryRegistryService = batteryRegistryService;
    }

    /**
     * API to register batteries
     */
    @Operation(
            summary = "Register Batteries",
            description = "Register batteries in bulk which is sent in the request payload."
    )
    @PostMapping(value = "/register" , consumes = MediaType.APPLICATION_JSON_VALUE )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(
                    schema = @Schema()
            )),
            @ApiResponse(responseCode = "401", description = "Authentication failure", content = @Content(
                    schema = @Schema()
            )),
            @ApiResponse(responseCode = "500", description = "Unexpected errors in service", content = @Content(
                    schema = @Schema()
            )),
    })
    public EnvelopedResponse<Map<String, String>> registerBatteries(@Valid @RequestBody List<BatteryDTO> batteries) {
         RegistryResponseWrapper wrapper = batteryRegistryService.registerBatteries(batteries);

         EnvelopedResponse<Map<String,String>> response = new EnvelopedResponse<>();
         response.setData(wrapper.getData());
         response.setErrors(wrapper.getErrorMessages());
         boolean hasError = wrapper.getErrorMessages() != null && wrapper.getErrorMessages().isEmpty();
         response.setHasError(hasError);

         return response;
    }
}
