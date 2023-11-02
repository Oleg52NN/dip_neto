package com.example.dip_neto.repositories;

import com.example.dip_neto.model.FileEntity;
import com.example.dip_neto.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    @Query(value = "SELECT * FROM files f WHERE f.user_id = :userid LIMIT :limitCount", nativeQuery = true)
    List<FileEntity> findByUserIdAndLimit(
            @Param("userid") Long userId,
            @Param("limitCount") int limitCount);

    Boolean existsByNameAndUser(String filename, Users user);

    void removeByNameAndUser_Id(String filename, Long idUser);

    Optional<FileEntity> findByNameAndUser(String filename, Users user);
}
