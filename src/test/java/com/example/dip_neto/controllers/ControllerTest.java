package com.example.dip_neto.controllers;

import com.example.dip_neto.model.FileEntity;
import com.example.dip_neto.model.Users;
import com.example.dip_neto.services.FileService;
import com.example.dip_neto.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private FileService fileService;
    @InjectMocks
    private UserController userController;

    @Test
    public void testShowAllFiles() {
        Principal principal = new UsernamePasswordAuthenticationToken("user", "password");
        Users user = new Users();
        user.setId(1L);
        when(userService.findByUsername(principal.getName())).thenReturn(user);

        LocalDate date = LocalDate.now();
        List<FileEntity> fileList =  List.of(
                new FileEntity(1L, "file1", new byte[128], 128L, date, user),
                new FileEntity(2L, "file2", new byte[512], 512L, date, user)
        );

        when(fileService.getFileListByUserId(user.getId(), 10)).thenReturn(fileList);

        ResponseEntity<Object> responseEntity = ResponseEntity.ok(userController.showAllFiles(10, principal));
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(fileList, responseEntity.getBody());
    }
}

