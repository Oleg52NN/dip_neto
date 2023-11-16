package com.example.dip_neto.services.interfaces;

import com.example.dip_neto.dto.FileDto;
import com.example.dip_neto.model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileServiceInterface {
    public List<FileEntity> getFileListByUserId(Long id, int limit);

    public FileEntity upload(MultipartFile file, String username) throws IOException;

    public void delete(String username, String filename);

    public FileEntity rename(String filename, FileDto newFile, String userName);

    public FileEntity getFile(String filename, String username);
}
