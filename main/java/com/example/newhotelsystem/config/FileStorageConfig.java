package com.example.newhotelsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration class for file storage.
 */
@Configuration
public class FileStorageConfig {

    @Value("${app.storage.directory:data}")
    private String dataDirectory;

    /**
     * Initialize the data directory.
     * 
     * @return the data directory path
     */
    @Bean
    @Primary
    public String dataDirectory() {
        Path dirPath = Paths.get(dataDirectory);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create data directory: " + dataDirectory, e);
            }
        }
        return dataDirectory;
    }
}