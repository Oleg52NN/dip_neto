package com.example.dip_neto.services;


import com.example.dip_neto.dto.FileDto;
import com.example.dip_neto.exeptions.AuthException;
import com.example.dip_neto.exeptions.DataException;
import com.example.dip_neto.model.FileEntity;
import com.example.dip_neto.model.Users;
import com.example.dip_neto.repositories.FileRepository;
import com.example.dip_neto.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileService {
    private final FileRepository filesRepository;
    private final UserRepository userRepository;

    public List<FileEntity> getFileListByUserId(Long id, int limit) {
        return filesRepository.findByUserIdAndLimit(id, limit);
    }

    @Transactional
    public FileEntity upload(MultipartFile file, String username) throws IOException {
        if (file.isEmpty() && file.getSize() == 0) {
            throw new DataException("Неверное имя файла ", HttpStatus.BAD_REQUEST);
        }

        Users user = userRepository.findByUsername(username).orElseThrow(() ->
                new AuthException("Пользователь не найден", HttpStatus.UNAUTHORIZED));

        var fileEntity = FileEntity.builder()
                .size(file.getSize())
                .data(file.getBytes())
                .name(generateFilename(file.getOriginalFilename(), user))
                .created(LocalDate.now())
                .user(user).build();

        log.info("IN upload - file : " + file.getOriginalFilename());

        return filesRepository.save(fileEntity);
    }

    @Transactional
    public void delete(String username, String filename) {
        Users user = userRepository.findByUsername(username).orElseThrow(() ->
                new AuthException("Пользователь не найден", HttpStatus.UNAUTHORIZED));

        if (filesRepository.existsByNameAndUser(filename, user)) {
            log.info("IN delete file WHERE filename = " + filename + " AND user.id = " + user.getId());
            filesRepository.removeByNameAndUser_Id(filename, user.getId());
        } else {
            throw new DataException("Файла не существует", HttpStatus.BAD_REQUEST);
        }
    }

    public FileEntity rename(String filename, FileDto newFile, String userName) {
        Users user = userRepository.findByUsername(userName).orElseThrow(() ->
                new AuthException("User not found!", HttpStatus.UNAUTHORIZED));

        FileEntity fileEntity = filesRepository.findByNameAndUser(filename, user).orElseThrow(() ->
                new DataException("Файла не существует", HttpStatus.BAD_REQUEST));

        fileEntity.setName(newFile.getFilename());

        return filesRepository.save(fileEntity);
    }

    public FileEntity getFile(String filename, String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(() ->
                new AuthException("Пользователь не найден", HttpStatus.UNAUTHORIZED));

        return filesRepository.findByNameAndUser(filename, user).orElseThrow(() ->
                new DataException("Файла не существует", HttpStatus.BAD_REQUEST));
    }

    private String generateFilename(String filename, Users user) {
        if (filesRepository.existsByNameAndUser(filename, user)) {
            filename = generateFilename("- " + filename, user);
        }

        return filename;
    }
}