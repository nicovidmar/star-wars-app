package com.starwars.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AuthTest {

    private final WebTestClient webTestClient;

    @Autowired
    public AuthTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    public void testLoginOK() {
        webTestClient.post()
                .uri("/login?username=user&password=password")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String token = response.getResponseBody();
                    assertNotNull(token);
                    System.out.println("JWT: " + token);
                });
    }

    @Test
    public void testUnauthorizedLogin() {
        webTestClient.post()
                .uri("/login?username=asd&password=asd")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void testProtectedRouteWithoutToken() {
        webTestClient.get()
                .uri("/people")
                .exchange()
                .expectStatus().isUnauthorized();
    }

}
