package com.example.dip_neto.jwtoken;

import com.example.dip_neto.model.Users;
import com.example.dip_neto.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users result = userService.findByUsername(username);

        if (result == null) {
            throw new UsernameNotFoundException("Пользователь с именем: " + username + " не найден");
        }

        TokenUser tokenUser = UserFactory.create(result);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);

        return tokenUser;
    }
}
