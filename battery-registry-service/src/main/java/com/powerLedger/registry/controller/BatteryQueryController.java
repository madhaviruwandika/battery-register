package com.powerLedger.registry.controller;

import com.powerLedger.registry.dao.Entity.Battery;
import com.powerLedger.registry.model.*;
import com.powerLedger.registry.service.BatteryQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/battery", produces = MediaType.APPLICATION_JSON_VALUE)
public class BatteryQueryController {
    BatteryQueryService batteryQueryService;

    @Autowired
    public void setBatteryQueryService(BatteryQueryService batteryQueryService) {
        this.batteryQueryService = batteryQueryService;
    }

    /**
     * API to return batteries which is in given post code range
     */
    @Operation(
            summary = "Query Batteries",
            description = "Query batteries based on the given filter criteria."
    )
    @RequestMapping(value = "/post-codes", method = RequestMethod.GET)
    @Parameters(value = {
            @Parameter(
                    name = "postCodeFrom",
                    in = ParameterIn.QUERY,
                    schema = @Schema(implementation = Long.class),
                    description = "Starting post code number)",
                    example = ""),
            @Parameter(
                    name = "postCodeTo",
                    in = ParameterIn.QUERY,
                    schema = @Schema(implementation = Long.class),
                    description = "Ending post code number)",
                    required = true,
                    example = ""),
            @Parameter(
                    name = "sortBy",
                    in = ParameterIn.QUERY,
                    schema = @Schema(implementation = SortBy.class),
                    description = "Sorting Method [NAME, WATT_CAPACITY]",
                    required = true,
                    example = "NAME"),
            @Parameter(
                    name = "sortOrder",
                    in = ParameterIn.QUERY,
                    schema = @Schema(implementation = SortOrder.class),
                    description = "Sort Order [ASC, DESC])",
                    required = true,
                    example = "ASC")
    })
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
    public EnvelopedResponse<List<Battery>> getBatteries(@Validated @ModelAttribute BatteryFilterDTO batteryFilterDTO) {
        QueryResponseWrapper response = batteryQueryService.getFilteredBatteriesByPostCode(batteryFilterDTO);
        EnvelopedResponse envelopedResponse = new EnvelopedResponse();
        envelopedResponse.setData(response.getData());
        envelopedResponse.setMeta(response.getMeta());
        envelopedResponse.setHasError(false);

        return envelopedResponse;
    }

    /**
     * API to return batteries which has less watt capacity than the given value
     */
    @Operation(
            summary = "Query Batteries",
            description = "Query batteries below the given watt capacity."
    )
    @RequestMapping(value = "/watt-capacity/{capacity}/below", method = RequestMethod.GET)
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

    public EnvelopedResponse<List<Battery>> getBatteriesBellowWattCapacity(@PathVariable("capacity") int capacity) {
        QueryResponseWrapper response = batteryQueryService.getFilteredBatteriesByWattCapacity(capacity);
        EnvelopedResponse envelopedResponse = new EnvelopedResponse();
        envelopedResponse.setData(response.getData());
        envelopedResponse.setMeta(response.getMeta());
        envelopedResponse.setHasError(false);

        return envelopedResponse;
    }


}
