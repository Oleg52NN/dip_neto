package com.example.dip_neto.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "roles")

public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Users> users;

    @Override
    public String toString() {
        return "Role{" +
                "id: " + getId() + ", " +
                "name: " + name + "}";
    }
}
