package com.starwars.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.starwars.constants.Constants;
import com.starwars.entity.Film;
import com.starwars.entity.Person;
import com.starwars.entity.Starship;
import com.starwars.entity.Vehicle;
import com.starwars.exception.CustomizableException;
import com.starwars.exception.handler.WebClientErrorHandler;
import com.starwars.response.multiple.AllPeopleResponse;
import com.starwars.response.multiple.AllStarshipsResponse;
import com.starwars.response.multiple.AllVehiclesResponse;
import com.starwars.response.multiple.FilmsResponse;
import com.starwars.response.multiple.PeopleResponse;
import com.starwars.response.multiple.StarshipsResponse;
import com.starwars.response.multiple.VehiclesResponse;
import com.starwars.response.multiple.AllPeopleResponse.PersonSummary;
import com.starwars.response.multiple.AllStarshipsResponse.StarshipSummary;
import com.starwars.response.multiple.AllVehiclesResponse.VehicleSummary;
import com.starwars.response.single.FilmResponse;
import com.starwars.response.single.PersonResponse;
import com.starwars.response.single.StarshipResponse;
import com.starwars.response.single.VehicleResponse;

import reactor.core.publisher.Mono;

@Service
public class StarWarsApiService {

        private final WebClient webClient;

        public StarWarsApiService(WebClient.Builder webClientBuilder) {
                this.webClient = webClientBuilder.baseUrl(Constants.BASE_STAR_WARS_API_URL)
                                .filter(WebClientErrorHandler.errorHandlingFilter())
                                .build();
        }

        public Mono<List<PersonSummary>> getPeople(int page) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path(Constants.PEOPLE_ENDPOINT)
                                                .queryParam(Constants.PAGE_QUERY_PARAM, page)
                                                .queryParam(Constants.LIMIT_QUERY_PARAM, Constants.LIMIT_VAL_TEN)
                                                .build())
                                .retrieve()
                                .bodyToMono(AllPeopleResponse.class)
                                .map(AllPeopleResponse::getResults);
        }

        public Mono<Person> getPersonById(String id) {
                return webClient.get()
                                .uri(Constants.PEOPLE_ENDPOINT + "/{id}", id)
                                .retrieve()
                                .bodyToMono(PersonResponse.class)
                                .map(res -> {
                                        Person person = res.getResult().getProperties();
                                        person.setUid(res.getResult().getUid());
                                        person.setDescription(res.getResult().getDescription());
                                        return person;
                                });
        }

        public Mono<List<Person>> getPersonByName(String name) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder.path(Constants.PEOPLE_ENDPOINT)
                                                .queryParam(Constants.NAME_QUERY_PARAM, name)
                                                .build())
                                .retrieve()
                                .bodyToMono(PeopleResponse.class)
                                .flatMap(resp -> {
                                        List<Person> people = Optional.ofNullable(resp.getResult())
                                                        .orElse(List.of())
                                                        .stream()
                                                        .map(result -> {
                                                                Person p = result.getProperties();
                                                                p.setUid(result.getUid());
                                                                p.setDescription(result.getDescription());
                                                                return p;
                                                        })
                                                        .collect(Collectors.toList());

                                        if (people.isEmpty()) {
                                                return Mono.error(new CustomizableException(Constants.PERSON_NOT_FOUND,
                                                                HttpStatus.NOT_FOUND));
                                        }

                                        return Mono.just(people);
                                });
        }

        public Mono<List<Film>> getFilms() {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder.path(Constants.FILMS_ENDPOINT)
                                                .build())
                                .retrieve()
                                .bodyToMono(FilmsResponse.class)
                                .map(resp -> Optional.ofNullable(resp.getResult())
                                                .orElse(List.of())
                                                .stream()
                                                .map(FilmsResponse.FilmsResult::getProperties)
                                                .collect(Collectors.toList()));
        }

        public Mono<Film> getFilmById(String id) {
                return webClient.get()
                                .uri(Constants.FILMS_ENDPOINT + "/{id}", id)
                                .retrieve()
                                .bodyToMono(FilmResponse.class)
                                .map(res -> res.getResult().getProperties());
        }

        public Mono<List<Film>> getFilmsByTitle(String title) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder.path(Constants.FILMS_ENDPOINT)
                                                .queryParam(Constants.TITLE_QUERY_PARAM, title)
                                                .build())
                                .retrieve()
                                .bodyToMono(FilmsResponse.class)
                                .flatMap(resp -> {
                                        List<Film> films = Optional.ofNullable(resp.getResult())
                                                        .orElse(List.of())
                                                        .stream()
                                                        .map(FilmsResponse.FilmsResult::getProperties)
                                                        .collect(Collectors.toList());

                                        if (films.isEmpty()) {
                                                return Mono.error(new CustomizableException(Constants.FILM_NOT_FOUND,
                                                                HttpStatus.NOT_FOUND));
                                        }
                                        return Mono.just(films);
                                });
        }

        public Mono<List<VehicleSummary>> getVehicles(int page) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder.path(Constants.VEHICLES_ENDPOINT)
                                                .queryParam(Constants.PAGE_QUERY_PARAM, page)
                                                .queryParam(Constants.LIMIT_QUERY_PARAM, Constants.LIMIT_VAL_TEN)
                                                .build())
                                .retrieve()
                                .bodyToMono(AllVehiclesResponse.class)
                                .map(AllVehiclesResponse::getResults);
        }

        public Mono<List<Vehicle>> getVehiclesByName(String name) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder.path(Constants.VEHICLES_ENDPOINT)
                                                .queryParam(Constants.NAME_QUERY_PARAM, name)
                                                .build())
                                .retrieve()
                                .bodyToMono(VehiclesResponse.class)
                                .flatMap(resp -> {
                                        List<Vehicle> vehicles = Optional.ofNullable(resp.getResult())
                                                        .orElse(List.of())
                                                        .stream()
                                                        .map(VehiclesResponse.VehicleResult::getProperties)
                                                        .collect(Collectors.toList());

                                        if (vehicles.isEmpty()) {
                                                return Mono.error(new CustomizableException(Constants.VEHICLE_NOT_FOUND,
                                                                HttpStatus.NOT_FOUND));
                                        }

                                        return Mono.just(vehicles);
                                });
        }

        public Mono<Vehicle> getVehicleById(String id) {
                return webClient.get()
                                .uri(Constants.VEHICLES_ENDPOINT + "/{id}", id)
                                .retrieve()
                                .bodyToMono(VehicleResponse.class)
                                .map(res -> res.getResult().getProperties());
        }

        public Mono<List<StarshipSummary>> getStarships(int page) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder.path(Constants.STARSHIPS_ENDPOINT)
                                                .queryParam(Constants.PAGE_QUERY_PARAM, page)
                                                .queryParam(Constants.LIMIT_QUERY_PARAM, Constants.LIMIT_VAL_TEN)
                                                .build())
                                .retrieve()
                                .bodyToMono(AllStarshipsResponse.class)
                                .map(AllStarshipsResponse::getResults);
        }

        public Mono<Starship> getStarshipById(String id) {
                return webClient.get()
                                .uri(Constants.STARSHIPS_ENDPOINT + "/{id}", id)
                                .retrieve()
                                .bodyToMono(StarshipResponse.class)
                                .map(res -> res.getResult().getProperties());
        }

        public Mono<List<Starship>> getStarshipsByName(String name) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder.path(Constants.STARSHIPS_ENDPOINT)
                                                .queryParam(Constants.NAME_QUERY_PARAM, name)
                                                .build())
                                .retrieve()
                                .bodyToMono(StarshipsResponse.class)
                                .flatMap(resp -> {
                                        List<Starship> starships = Optional.ofNullable(resp.getResult())
                                                        .orElse(List.of())
                                                        .stream()
                                                        .map(StarshipsResponse.StarshipResults::getProperties)
                                                        .collect(Collectors.toList());

                                        if (starships.isEmpty()) {
                                                return Mono.error(new CustomizableException(Constants.STARSHIP_NOT_FOUND,
                                                                HttpStatus.NOT_FOUND));
                                        }

                                        return Mono.just(starships);
                                });
        }

}
