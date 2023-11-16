package com.example.dip_neto.controllers;

import com.example.dip_neto.dto.AuthRequestDto;
import com.example.dip_neto.jwtoken.TokenCreator;
import com.example.dip_neto.model.Users;
import com.example.dip_neto.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenCreator tokenCreator;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto requestDto) {
        try {
            Users user = userService.findByEmail(requestDto.login());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), requestDto.password()));
            String token = tokenCreator.createToken(user.getUsername(), user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", user.getUsername());
            response.put("auth-token", token);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неверное имя пользователя или пароль");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("auth-token") String token) {
        var auth = tokenCreator.getAuthentication(token);
        auth.setAuthenticated(false);
        auth.getAuthorities().clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
