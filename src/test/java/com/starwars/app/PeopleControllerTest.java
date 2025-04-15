package com.starwars.app;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.Test;

import com.starwars.entity.Person;
import com.starwars.exception.CustomizableException;
import com.starwars.response.multiple.AllPeopleResponse;
import com.starwars.service.StarWarsApiService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PeopleControllerTest {

    private final WebTestClient webTestClient;

    @Autowired
    public PeopleControllerTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @MockBean
    private StarWarsApiService starWarsApiService;

    private String jwtToken;

    @Value("${jwt.secret}")
    private String secret;

    @BeforeEach
    void setup() {
        jwtToken = "Bearer " + Jwts.builder()
                .claim("role", "USER")
                .signWith(SignatureAlgorithm.HS256, Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    @Test
    public void testGetPeople() {
        List<AllPeopleResponse.PersonSummary> mockPeople = List.of(new AllPeopleResponse.PersonSummary() {
            {
                setName("Luke Skywalker");
            }
        });
        Mockito.when(starWarsApiService.getPeople(1)).thenReturn(Mono.just(mockPeople));

        webTestClient.get()
                .uri("/people?page=1")
                .header("Authorization", jwtToken)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class)
                .hasSize(1)
                .value(people -> Assertions.assertEquals("Luke Skywalker", people.get(0).getName()));
    }

    @Test
    public void testGetPeopleError() {
        Mockito.when(starWarsApiService.getPeople(1))
                .thenReturn(Mono
                        .error(new CustomizableException("Error fetching people", HttpStatus.INTERNAL_SERVER_ERROR)));

        webTestClient.get()
                .uri("/people?page=1")
                .header("Authorization", jwtToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(String.class)
                .isEqualTo("Error fetching people");
    }

    @Test
    public void testGetPeopleByName() {
        List<Person> mockPeople = List.of(
                new Person() {
                    {
                        setName("Luke Skywalker");
                    }
                },
                new Person() {
                    {
                        setName("Anakin Skywalker");
                    }
                });
        Mockito.when(starWarsApiService.getPersonByName("sky")).thenReturn(Mono.just(mockPeople));

        webTestClient.get()
                .uri("/people/search?name=sky")
                .header("Authorization", jwtToken)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class)
                .hasSize(2)
                .value(people -> {
                    List<String> names = people.stream().map(Person::getName).toList();
                    Assertions.assertTrue(names.contains("Luke Skywalker"));
                    Assertions.assertTrue(names.contains("Anakin Skywalker"));
                });
    }

    @Test
    public void testGetPersonById() {
        Person luke = new Person();
        luke.setName("Luke Skywalker");

        Mockito.when(starWarsApiService.getPersonById("1")).thenReturn(Mono.just(luke));

        webTestClient.get()
                .uri("/people/1")
                .header("Authorization", jwtToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class)
                .value(person -> Assertions.assertEquals("Luke Skywalker", person.getName()));
    }

    @Test
    public void testGetPersonByIdError() {
        Mockito.when(starWarsApiService.getPersonById("1"))
                .thenReturn(Mono.error(new CustomizableException("Person not found", HttpStatus.NOT_FOUND)));

        webTestClient.get()
                .uri("/people/1")
                .header("Authorization", jwtToken)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .isEqualTo("Person not found");
    }

}
