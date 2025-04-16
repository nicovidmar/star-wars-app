package com.starwars.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.constants.Constants;
import com.starwars.exception.CustomizableException;
import com.starwars.security.JwtUtil;
import com.starwars.swagger.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Auth", description = "Obtenga su JWT token con usuario 'user' y contraseña 'password'")
@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(ReactiveAuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Login", description = "Obtiene JWT token con las credenciales correctas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public Mono<String> login(@Parameter(description = "Nombre", example = "user") @RequestParam String username,
            @Parameter(description = "Contraseña", example = "password") @RequestParam String password) {
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password))
                .map(auth -> {
                    LOGGER.info(Constants.LOGIN_SUCCESS, username);
                    return jwtUtil.generateToken(username);
                })
                .onErrorResume(e -> {
                    LOGGER.error(Constants.LOGIN_FAILURE, username);
                    return Mono.error(new CustomizableException(Constants.INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED));
                });
    }
    
    @ExceptionHandler(CustomizableException.class)
    public ResponseEntity<ErrorResponse> handleCustomizableException(CustomizableException ex) {
        return new ResponseEntity<>(ex.toErrorResponse(), ex.getStatus());
    }
}
