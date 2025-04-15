package com.starwars.app;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.starwars.entity.Film;
import com.starwars.entity.Person;
import com.starwars.entity.Starship;
import com.starwars.entity.Vehicle;
import com.starwars.response.multiple.AllPeopleResponse.PersonSummary;
import com.starwars.response.multiple.AllStarshipsResponse.StarshipSummary;
import com.starwars.response.multiple.AllVehiclesResponse.VehicleSummary;
import com.starwars.service.StarWarsApiService;

@SpringBootTest
class AppApplicationTests {

	@Autowired
    private StarWarsApiService service;

    @Test
    public void testGetPeople() {
        List<PersonSummary> people = service.getPeople(1).block();
        assertNotNull(people);
        assertFalse(people.isEmpty());
        people.forEach(p -> System.out.println("Person: " + p.getName()));
    }

    @Test
    public void testGetPersonByName() {
        List<Person> results = service.getPersonByName("sky").block();
        assertNotNull(results);
        assertFalse(results.isEmpty());
        results.forEach(p -> System.out.println("Found: " + p.getName()));
    }

    @Test
    public void testGetPersonById() {
        Person person = service.getPersonById("1").block();
        assertNotNull(person);
        System.out.println("Person by ID: " + person.getName());
    }

	@Test
    public void testGetFilms() {
        List<Film> films = service.getFilms().block();
        assertNotNull(films);
        assertFalse(films.isEmpty());
        films.forEach(f -> System.out.println("Film: " + f.getTitle()));
    }

	@Test
    public void testGetFilmByTitle() {
        List<Film> results = service.getFilmsByTitle("a").block();
        assertNotNull(results);
        assertFalse(results.isEmpty());
        results.forEach(f -> System.out.println("Film: " + f.getTitle()));
    }

	@Test
    public void testGetFilmById() {
        Film film = service.getFilmById("1").block();
        assertNotNull(film);
        System.out.println("Film by ID: " + film.getTitle());
    }

	@Test
    public void testGetVehicle() {
        List<VehicleSummary> vehicles = service.getVehicles(1).block();
        assertNotNull(vehicles);
		assertFalse(vehicles.isEmpty());
        vehicles.forEach(v -> System.out.println("Vehicles: " + v.getName()));
    }

	@Test
    public void testGetVehiclesByName() {
        List<Vehicle> results = service.getVehiclesByName("er").block();
        assertNotNull(results);
        assertFalse(results.isEmpty());
        results.forEach(v -> System.out.println("Name: " + v.getName()));
    }

	@Test
    public void testGetVehiclesById() {
        Vehicle vehicle = service.getVehicleById("4").block();
        assertNotNull(vehicle);
        System.out.println("Vehicle by ID: " + vehicle.getName());
    }

	@Test
    public void testGetStarships() {
        List<StarshipSummary> starship = service.getStarships(1).block();
        assertNotNull(starship);
		assertFalse(starship.isEmpty());
        starship.forEach(s-> System.out.println("Starship: " + s.getName()));
    }

	@Test
    public void testGetStarshipsByName() {
        List<Starship> results = service.getStarshipsByName("star").block();
        assertNotNull(results);
        assertFalse(results.isEmpty());
        results.forEach(s -> System.out.println("Starship: " + s.getName()));
    }

	@Test
    public void testGetStarshipById() {
        Starship starship = service.getStarshipById("2").block();
        assertNotNull(starship);
        System.out.println("Starship by ID: " + starship.getName());
    }

}
