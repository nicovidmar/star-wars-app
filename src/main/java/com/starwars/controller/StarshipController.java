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
import com.starwars.entity.Starship;
import com.starwars.exception.CustomizableException;
import com.starwars.response.multiple.AllStarshipsResponse;
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
@Tag(name = "Starships", description = "Operaciones relacionadas con las naves del universo Star Wars")
@RestController
@RequestMapping("/starships")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class StarshipController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StarshipController.class);
    private final StarWarsApiService starWarsService;

    public StarshipController(StarWarsApiService starWarsService) {
        this.starWarsService = starWarsService;
    }

    @Operation(summary = "Listar naves", description = "Obtiene una lista paginada de naves")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Naves encontradas"),
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public Mono<List<AllStarshipsResponse.StarshipSummary>> getStarships(
            @Parameter(description = "Número de página a solicitar", example = "1")
            @RequestParam(defaultValue = "1") int page) {
        LOGGER.info(Constants.STARSHIP_LOG_FETCH, page);
        return starWarsService.getStarships(page)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.STARSHIP_FETCH_ERROR, e);
                    return Mono.error(new CustomizableException(Constants.STARSHIP_FETCH_ERROR, HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @Operation(summary = "Buscar naves por nombre", description = "Obtiene una lista de naves cuyo nombre contiene el string proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Naves encontradas"),
        @ApiResponse(responseCode = "404", description = "Naves no encontradas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public Mono<List<Starship>> getStarshipsByName(
            @Parameter(description = "Nombre o parte del nombre de la nave", example = "Star")
            @RequestParam String name) {
        LOGGER.info(Constants.STARSHIP_LOG_FETCH_BY_NAME, name);
        return starWarsService.getStarshipsByName(name)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.STARSHIP_FETCH_ERROR_BY_NAME, e);
                    return Mono.error(new CustomizableException(Constants.STARSHIP_FETCH_ERROR_BY_NAME, HttpStatus.NOT_FOUND));
                });
    }

    @Operation(summary = "Buscar nave por ID", description = "Obtiene una nave específica según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Nave encontrada"),
        @ApiResponse(responseCode = "404", description = "Nave no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public Mono<Starship> getStarshipById(
            @Parameter(description = "ID de la nave a obtener", example = "2")
            @PathVariable String id) {
        LOGGER.info(Constants.STARSHIP_LOG_FETCH_BY_ID, id);
        return starWarsService.getStarshipById(id)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.STARSHIP_NOT_FOUND, e);
                    return Mono.error(new CustomizableException(Constants.STARSHIP_NOT_FOUND, HttpStatus.NOT_FOUND));
                });
    }

    @ExceptionHandler(CustomizableException.class)
    public ResponseEntity<String> handleCustomizableException(CustomizableException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
}
