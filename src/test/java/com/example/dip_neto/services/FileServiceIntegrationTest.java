package com.example.dip_neto.services;


import com.example.dip_neto.exeptions.AuthException;
import com.example.dip_neto.exeptions.DataException;
import com.example.dip_neto.model.Users;
import com.example.dip_neto.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.testcontainers.containers.GenericContainer;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileServiceIntegrationTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileService fileService;
    private static final String DB_NAME = "database";

    private final Users user = Users.builder().username("user").build();
    private static final String EMPTY = "empty";
    public static GenericContainer<?> app = new GenericContainer("dip_neto-app:latest")
            .withExposedPorts(8080)
            .withEnv("SPRING_DATASOURCE_URL", "jdbc:potgresql://" +  DB_NAME + ":5432/potgres")
            .withEnv("SPRING_LIQUIBASE_URL", "jdbc:potgresql://" +  DB_NAME + ":5432/potgres");

    @BeforeAll
    public static void setUp() {
        app.start();
    }

    @Test
    void getFileTestWhenFileNotFound() {
        Optional<Users> user = userRepository.findById(1L);

        if (user.isPresent()) {
            Exception thrown = Assertions.assertThrows(
                    DataException.class, () -> fileService.getFile(EMPTY, user.get().getUsername()));
            Assertions.assertEquals("File not found", thrown.getMessage());
        }
    }

    @Test
    void getFileTestWhenUserNotFound() {
        Exception thrown = Assertions.assertThrows(
                AuthException.class, () -> fileService.getFile(EMPTY, EMPTY));
        Assertions.assertEquals("User not found", thrown.getMessage());
    }

    @Test
    void uploadEmptyMultipartFile() {
        MockMultipartFile multipartFileMock = new MockMultipartFile("filename",
                "filename",
                "text/plain",
                new byte[]{});

        RuntimeException exception = Assertions.assertThrows(
                DataException.class, () -> fileService.upload(multipartFileMock, user.getUsername()));

        Assertions.assertEquals("Incorrect file name", exception.getMessage());

    }
}