package com.example.dip_neto.services;

import com.example.dip_neto.exeptions.AuthException;
import com.example.dip_neto.model.Users;
import com.example.dip_neto.repositories.UserRepository;
import com.example.dip_neto.services.interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new AuthException("Пользователь по адресу почты не найден", HttpStatus.UNAUTHORIZED));
    }
    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new AuthException("Пользователь с таким именем не найден", HttpStatus.UNAUTHORIZED));
    }

}
