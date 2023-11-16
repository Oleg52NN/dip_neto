package com.example.dip_neto.exceptions;

import com.example.dip_neto.enums.Status;
import com.example.dip_neto.exeptions.AuthException;
import com.example.dip_neto.model.FileEntity;
import com.example.dip_neto.model.Users;
import com.example.dip_neto.repositories.FileRepository;
import com.example.dip_neto.services.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExceptionTest {
    public static final String TOKEN = "Token";
    public static final Users USER = new Users(1L, "userTest", "user@mail.ru", "pass", null, null, null, Status.ACTIVE);
    public static final FileEntity FILE = new FileEntity(1L, "file1", new byte[128], 128L, LocalDate.now(), USER);
    public static final String FILENAME = "Filename";
    public static final byte[] FILE_CONTENT = FILENAME.getBytes();
    public static final MultipartFile MULTIPART_FILE = new MockMultipartFile(FILENAME, FILE_CONTENT);
    @InjectMocks
    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    @Test
    void uploadFileIfUnauthorized() {
        assertThrows(AuthException.class, () -> fileService.upload(MULTIPART_FILE, FILENAME));
    }
    @Test
    void deleteFileIfUnauthorized() {
        assertThrows(AuthException.class, () -> fileService.delete(TOKEN, FILENAME));
    }
    @Test
    void deleteFileException() {
        Mockito.when(fileRepository.findByNameAndUser(FILENAME, USER)).thenReturn(Optional.of(FILE));
        assertThrows(AuthException.class, () -> fileService.delete(TOKEN, FILENAME));
    }


}