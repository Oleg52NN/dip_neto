package com.example.dip_neto.services.interfaces;

import com.example.dip_neto.model.Users;

public interface UserServiceInterface {

    public Users findByEmail(String email);

    public Users findByUsername(String username);

}
