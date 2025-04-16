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
import com.starwars.entity.Film;
import com.starwars.exception.CustomizableException;
import com.starwars.service.StarWarsApiService;
import com.starwars.swagger.ErrorResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import reactor.core.publisher.Mono;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Films", description = "Operaciones relacionadas con las películas del universo Star Wars")
@RestController
@RequestMapping("/films")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class FilmController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilmController.class);
    private final StarWarsApiService starWarsService;

    public FilmController(StarWarsApiService starWarsService) {
        this.starWarsService = starWarsService;
    }

    @Operation(summary = "Listar todas las películas", description = "Obtiene una lista de todas las películas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Película encontrada"),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public Mono<List<Film>> getAllFilms() {
        LOGGER.info(Constants.FILM_LOG_FETCH_ALL);
        return starWarsService.getFilms()
                .onErrorResume(e -> {
                    LOGGER.error(Constants.FILMS_FETCH_ERROR, e);
                    return Mono.error(
                            new CustomizableException(Constants.FILMS_FETCH_ERROR, HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @Operation(summary = "Buscar películas por título", description = "Obtiene películas que coincidan con el título dado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Película encontrada"),
            @ApiResponse(responseCode = "404", description = "Película no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public Mono<List<Film>> getFilmsByTitle(
            @Parameter(description = "Título o parte del título de la película a buscar", example = "Hope") @RequestParam String title) {
        LOGGER.info(Constants.FILM_LOG_FETCH, title);
        return starWarsService.getFilmsByTitle(title)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.FILMS_FETCH_ERROR_BY_TITLE, e);
                    return Mono.error(
                            new CustomizableException(Constants.FILMS_FETCH_ERROR_BY_TITLE, HttpStatus.NOT_FOUND));
                });
    }

    @Operation(summary = "Buscar película por ID", description = "Obtiene una película según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Película encontrada"),
            @ApiResponse(responseCode = "404", description = "Película no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public Mono<Film> getFilmById(
            @Parameter(description = "ID de la película a obtener", example = "1") @PathVariable String id) {
        LOGGER.info(Constants.FILM_LOG_FETCH_BY_ID, id);
        return starWarsService.getFilmById(id)
                .onErrorResume(e -> {
                    LOGGER.error(Constants.FILM_NOT_FOUND, e);
                    return Mono.error(new CustomizableException(Constants.FILM_NOT_FOUND, HttpStatus.NOT_FOUND));
                });
    }

    @ExceptionHandler(CustomizableException.class)
    public ResponseEntity<ErrorResponse> handleCustomizableException(CustomizableException ex) {
        return new ResponseEntity<>(ex.toErrorResponse(), ex.getStatus());
    }
}