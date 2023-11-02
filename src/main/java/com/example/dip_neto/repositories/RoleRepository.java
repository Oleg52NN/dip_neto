package com.example.dip_neto.repositories;

import com.example.dip_neto.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}