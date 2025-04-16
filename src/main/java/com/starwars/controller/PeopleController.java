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
import com.starwars.entity.Person;
import com.starwars.exception.CustomizableException;
import com.starwars.response.multiple.AllPeopleResponse;
import com.starwars.service.StarWarsApiService;
import com.starwars.swagger.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "People", description = "Operaciones relacionadas con los personajes del universo Star Wars")
@RestController
@RequestMapping("/people")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class PeopleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleController.class);
    private final StarWarsApiService starWarsApiService;

    public PeopleController(StarWarsApiService starWarsApiService) {
        this.starWarsApiService = starWarsApiService;
    }

    @Operation(summary = "Buscar personajes", description = "Obtiene una lista paginada de personajes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personajes encontrados"),
            @ApiResponse(responseCode = "400", description = "Sin resultados para página", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("")
    public Mono<List<AllPeopleResponse.PersonSummary>> getPeople(
            @Parameter(description = "Número de página a solicitar", example = "2") @RequestParam(defaultValue = "1") int page) {

        LOGGER.info(Constants.PEOPLE_LOG_FETCH, page);
        return starWarsApiService.getPeople(page)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.PEOPLE_FETCH_ERROR, e);
                    if (e instanceof CustomizableException) {
                        return Mono.error(e);
                    }
                    return Mono.error(new CustomizableException(Constants.PEOPLE_FETCH_ERROR,
                            HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @Operation(summary = "Buscar personajes por nombre", description = "Obtiene lista de personajes cuyos nombres contengan el String pasado por parámetro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personajes encontrados"),
            @ApiResponse(responseCode = "404", description = "Personajes no encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public Mono<List<Person>> getPeopleByName(
            @Parameter(description = "Nombre o parte del nombre del personaje", example = "Sky") @RequestParam String name) {

        LOGGER.info(Constants.PEOPLE_LOG_FETCH_BY_NAME, name);
        return starWarsApiService.getPersonByName(name)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.PEOPLE_FETCH_ERROR_BY_NAME, e);
                    return Mono.error(
                            new CustomizableException(Constants.PEOPLE_FETCH_ERROR_BY_NAME, HttpStatus.NOT_FOUND));
                });
    }

    @Operation(summary = "Buscar personaje por ID", description = "Obtiene un personaje según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personaje encontrado"),
            @ApiResponse(responseCode = "404", description = "Personajes no encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public Mono<Person> getPersonById(
            @Parameter(description = "ID de personaje a obtener", example = "1") @PathVariable String id) {

        LOGGER.info(Constants.PEOPLE_LOG_FETCH_BY_ID, id);
        return starWarsApiService.getPersonById(id)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.PERSON_NOT_FOUND, e);
                    return Mono.error(new CustomizableException(Constants.PERSON_NOT_FOUND, HttpStatus.NOT_FOUND));
                });
    }

    @ExceptionHandler(CustomizableException.class)
    public ResponseEntity<ErrorResponse> handleCustomizableException(CustomizableException ex) {
        return new ResponseEntity<>(ex.toErrorResponse(), ex.getStatus());
    }
}
