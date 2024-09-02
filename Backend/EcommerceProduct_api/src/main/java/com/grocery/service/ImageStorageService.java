package com.grocery.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.grocery.exception.ImageServiceException;

@Service
public class ImageStorageService {
	
	private final Path rootLocation = Paths.get("src/main/resources/static/images");
	
    public ImageStorageService() throws ImageServiceException {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new ImageServiceException("Could not create folder");
        }
    }
    
    private boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/png") || contentType.equals("image/jpeg"));
    }
    
    public String storeFile(MultipartFile file) throws ImageServiceException {
        if (file == null || file.getOriginalFilename() == null) {
            throw new ImageServiceException("File and filename must not be null");
        }
        
        if(!isValidImageFile(file)) {
        	throw new ImageServiceException("Only jpeg and png are allowed to be uploaded.");
        }

        try {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path destinationFile = this.rootLocation.resolve(
                Paths.get(filename)).normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            return "/images/" + filename;
        } catch (IOException e) {
            throw new ImageServiceException("Failed to store file " + file.getOriginalFilename());
        }
    }	

}
