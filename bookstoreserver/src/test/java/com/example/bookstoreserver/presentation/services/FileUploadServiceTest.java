package com.example.bookstoreserver.presentation.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

@ExtendWith(MockitoExtension.class)
public class FileUploadServiceTest {
    
    @InjectMocks
    private FileUploadService fileUploadService;

    @Mock
    private MultipartFile multipartFile;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        String dir = tempDir.toString() + File.separator;
        ReflectionTestUtils.setField(fileUploadService, "uploadDir", dir);
    }

     @Test
    void testUploadMultipart() throws Exception {
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(testImage, "jpeg", baos);

        when(multipartFile.getInputStream())
                .thenReturn(new ByteArrayInputStream(baos.toByteArray()));

        try (MockedStatic<ServletUriComponentsBuilder> mockedBuilder =
                     mockStatic(ServletUriComponentsBuilder.class)) {

            ServletUriComponentsBuilder builder = mock(ServletUriComponentsBuilder.class);
            UriComponents uriComponents = mock(UriComponents.class);

            mockedBuilder.when(ServletUriComponentsBuilder::fromCurrentContextPath)
                    .thenReturn(builder);
            when(builder.build()).thenReturn(uriComponents);
            when(uriComponents.toUriString()).thenReturn("http://localhost:8080");

            String result = fileUploadService.uploadMultipart(multipartFile);

            assertNotNull(result);
            assertTrue(result.startsWith("http://localhost:8080/uploads/"));
            assertTrue(result.endsWith(".jpg"));
        }
    }
    
}
