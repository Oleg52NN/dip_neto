package com.example.dip_neto.services;


import com.example.dip_neto.model.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {
    @Autowired
    UserService userService;
    private static final String DB_NAME = "database";

    public static GenericContainer<?> app = new GenericContainer("dip_neto-app:latest")
            .withExposedPorts(8080)
            .withEnv("SPRING_DATASOURCE_URL", "jdbc:postgresql://" + DB_NAME + ":5432/postgres")
            .withEnv("SPRING_LIQUIBASE_URL", "jdbc:postgresql://" + DB_NAME + ":5432/postgres");

    private final Users testUser = Users.builder()
            .username("user")
            .email("nouser@mail.ru")
            .password("pass").build();

    @BeforeAll
    public static void setUp() {
        app.start();
    }

    @Test
    public void testFindByUsername() {
        Users user = userService.findByUsername(testUser.getUsername());

        assertNotNull(user);
        Assertions.assertEquals(testUser.getUsername(), user.getUsername());
    }

    @Test
    public void testFindByEmail() {
        Users user = userService.findByEmail(testUser.getEmail());

        assertNotNull(user);
        Assertions.assertEquals(testUser.getEmail(), user.getEmail());
    }
}