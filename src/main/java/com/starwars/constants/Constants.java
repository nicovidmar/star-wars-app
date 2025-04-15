package com.starwars.constants;

public final class Constants {

    public static final String BASE_STAR_WARS_API_URL = "https://www.swapi.tech/api";

    public static final String PEOPLE_ENDPOINT = "/people";
    public static final String FILMS_ENDPOINT = "/films";
    public static final String VEHICLES_ENDPOINT = "/vehicles";
    public static final String STARSHIPS_ENDPOINT = "/starships";


    public static final String LOGIN_ENDPOINT = "/login";

    public static final String ROLE_USER = "USER";

    public static final String NAME_QUERY_PARAM = "name";
    public static final String PAGE_QUERY_PARAM = "page";
    public static final String LIMIT_QUERY_PARAM = "limit";
    public static final int LIMIT_VAL_TEN = 10;
    public static final String TITLE_QUERY_PARAM = "title";

    public static final String PEOPLE_FETCH_ERROR = "Error fetching people";
    public static final String PEOPLE_FETCH_ERROR_BY_NAME = "Error fetching person by name";
    public static final String PERSON_NOT_FOUND = "Person not found";
    public static final String PEOPLE_LOG_FETCH = "Fetching people for page: {}";
    public static final String PEOPLE_LOG_FETCH_BY_NAME = "Fetching person with name: {}";
    public static final String PEOPLE_LOG_FETCH_BY_ID = "Fetching person with id: {}";

    public static final String FILMS_FETCH_ERROR = "Error fetching films";
    public static final String FILMS_FETCH_ERROR_BY_TITLE = "Error fetching films by title";
    public static final String FILM_NOT_FOUND = "Film not found";
    public static final String FILM_LOG_FETCH = "Fetching films for title: {}";
    public static final String FILM_LOG_FETCH_ALL = "Fetching all films for page: {}";
    public static final String FILM_LOG_FETCH_BY_ID = "Fetching film with id: {}";

    public static final String STARSHIP_FETCH_ERROR = "Error fetching starships";
    public static final String STARSHIP_FETCH_ERROR_BY_NAME = "Error fetching starships by name";
    public static final String STARSHIP_NOT_FOUND = "Starship not found";
    public static final String STARSHIP_LOG_FETCH = "Fetching starships for page: {}";
    public static final String STARSHIP_LOG_FETCH_BY_NAME = "Fetching starships with name: {}";
    public static final String STARSHIP_LOG_FETCH_BY_ID = "Fetching starship with id: {}";
    
    public static final String VEHICLE_FETCH_ERROR = "Error fetching vehicles";
    public static final String VEHICLE_FETCH_ERROR_BY_NAME = "Error fetching vehicles by name";
    public static final String VEHICLE_NOT_FOUND = "Vehicle not found";
    public static final String VEHICLE_LOG_FETCH = "Fetching vehicles for page: {}";
    public static final String VEHICLE_LOG_FETCH_BY_NAME = "Fetching vehicles with name: {}";
    public static final String VEHICLE_LOG_FETCH_BY_ID = "Fetching vehicle with id: {}";

    public static final String ERROR_LOG_TEMPLATE = "Error {}: {}";
    public static final String ERROR_MESSAGE_TEMPLATE = "Error %s: %s";

    public static final String REQUEST_LOG_TEMPLATE = "Request: {} {}";
    public static final String DEFAULT_ERROR_BODY = "Sin contenido de error";

    
    public static final String NOT_FOUND_MESSAGE = "Not found";

    public static final String LOGIN_SUCCESS = "Autenticación exitosa para el usuario: {}";
    public static final String LOGIN_FAILURE = "Error de autenticación para el usuario: {}";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";

    public static final String CUSTOM_EXCEPTION_LOG = "Excepción personalizada atrapada: {}";
    public static final String GENERAL_EXCEPTION_LOG = "Excepción general atrapada: {}";
    public static final String UNEXPECTED_ERROR_MESSAGE = "Error inesperado: ";

    public static final String UNAUTHORIZED_ACCESS_MESSAGE = "No estás autenticado. Por favor, inicia sesión.";
    public static final String UNAUTHORIZED_ACCESS_LOG = "Intento de acceso no autorizado.";
}
