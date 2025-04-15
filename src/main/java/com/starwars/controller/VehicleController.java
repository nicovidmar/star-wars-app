package com.starwars.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.constants.Constants;
import com.starwars.entity.Vehicle;
import com.starwars.exception.CustomizableException;
import com.starwars.response.multiple.AllVehiclesResponse;
import com.starwars.service.StarWarsApiService;
import com.starwars.swagger.ErrorResponse;

import reactor.core.publisher.Mono;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/vehicles")
@PreAuthorize("hasAuthority('ROLE_USER')")
@Tag(name = "Vehicles", description = "Operaciones relacionadas con los vehículos del universo Star Wars")
public class VehicleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);
    private final StarWarsApiService starWarsService;

    public VehicleController(StarWarsApiService starWarsService) {
        this.starWarsService = starWarsService;
    }

    @Operation(summary = "Listar vehículos", description = "Obtiene una lista paginada de vehículos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículos encontrados"),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public Mono<List<AllVehiclesResponse.VehicleSummary>> getVehicles(
            @Parameter(description = "Número de página a solicitar", example = "1")
            @RequestParam(defaultValue = "1") int page) {
        LOGGER.info(Constants.VEHICLE_LOG_FETCH, page);
        return starWarsService.getVehicles(page)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.VEHICLE_FETCH_ERROR, e);
                    return Mono.error(new CustomizableException(Constants.VEHICLE_FETCH_ERROR, HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @Operation(summary = "Buscar vehículos por nombre", description = "Obtiene lista de vehículos cuyos nombres contengan el String pasado por parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículos encontrados"),
            @ApiResponse(responseCode = "404", description = "Vehículos no encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public Mono<List<Vehicle>> getVehiclesByName(
            @Parameter(description = "Nombre o parte del nombre del vehículo", example = "Sand")
            @RequestParam String name) {
        LOGGER.info(Constants.VEHICLE_LOG_FETCH_BY_NAME, name);
        return starWarsService.getVehiclesByName(name)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.VEHICLE_FETCH_ERROR_BY_NAME, e);
                    return Mono.error(new CustomizableException(Constants.VEHICLE_FETCH_ERROR_BY_NAME, HttpStatus.NOT_FOUND));
                });
    }

    @Operation(summary = "Buscar vehículo por ID", description = "Obtiene un vehículo según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public Mono<Vehicle> getVehicleById(
            @Parameter(description = "ID del vehículo a obtener", example = "4")
            @PathVariable String id) {
        LOGGER.info(Constants.VEHICLE_LOG_FETCH_BY_ID, id);
        return starWarsService.getVehicleById(id)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.VEHICLE_NOT_FOUND, e);
                    return Mono.error(new CustomizableException(Constants.VEHICLE_NOT_FOUND, HttpStatus.NOT_FOUND));
                });
    }

    @ExceptionHandler(CustomizableException.class)
    public ResponseEntity<String> handleCustomizableException(CustomizableException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
}
