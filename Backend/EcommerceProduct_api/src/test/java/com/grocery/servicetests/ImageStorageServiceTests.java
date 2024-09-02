package com.grocery.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.grocery.exception.ImageServiceException;
import com.grocery.service.ImageStorageService;

public class ImageStorageServiceTests {

	  @Mock
	    private MultipartFile multipartFile;

	    private ImageStorageService imageStorageService;
	    private final Path rootLocation = Paths.get("src/main/resources/static/images");

	    @BeforeEach
	    void setUp() throws ImageServiceException {
	        multipartFile = Mockito.mock(MultipartFile.class);
	        imageStorageService = new ImageStorageService();
	    }

	    @Test
	    void testStoreFile_nullFilename_throwsException() {
	        when(multipartFile.getOriginalFilename()).thenReturn(null);

	        ImageServiceException exception = assertThrows(ImageServiceException.class, () -> {
	            imageStorageService.storeFile(multipartFile);
	        });

	        assertEquals("File and filename must not be null", exception.getMessage());
	    }

	    @Test
	    void testStoreFile_invalidImageFile_throwsException() {
	        when(multipartFile.getOriginalFilename()).thenReturn("test-file.txt");
	        when(multipartFile.getContentType()).thenReturn("text/plain");

	        ImageServiceException exception = assertThrows(ImageServiceException.class, () -> {
	            imageStorageService.storeFile(multipartFile);
	        });

	        assertEquals("Only jpeg and png are allowed to be uploaded.", exception.getMessage());
	    }

	    @Test
	    void testStoreFile_validImageFile_returnsPath() throws IOException, ImageServiceException {
	        MockMultipartFile multipartFile = new MockMultipartFile("file", "test-image.png", "image/png", "image content".getBytes());

	        // Create the directory where the file will be stored
	        Files.createDirectories(rootLocation);

	        String result = imageStorageService.storeFile(multipartFile);

	        assertTrue(result.contains("/images/test-image.png"));
	        // Clean up the created file
	        Files.deleteIfExists(rootLocation.resolve("test-image.png"));
	    }

	    @Test
	    void testStoreFile_ioException_throwsException() throws IOException {
	        when(multipartFile.getOriginalFilename()).thenReturn("test-image.png");
	        when(multipartFile.getContentType()).thenReturn("image/png");
	        doThrow(IOException.class).when(multipartFile).getInputStream();

	        ImageServiceException exception = assertThrows(ImageServiceException.class, () -> {
	            imageStorageService.storeFile(multipartFile);
	        });

	        assertEquals("Failed to store file test-image.png", exception.getMessage());
	    }
}
