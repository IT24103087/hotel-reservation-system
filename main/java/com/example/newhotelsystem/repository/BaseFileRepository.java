package com.example.newhotelsystem.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Base repository implementation for file-based storage.
 * @param <T> the entity type
 */
public abstract class BaseFileRepository<T> {

    protected final ObjectMapper objectMapper;
    protected final String dataDirectory;
    protected final String entityName;
    protected final Class<T> entityClass;
    protected AtomicLong idCounter;

    /**
     * Constructor for BaseFileRepository.
     * 
     * @param dataDirectory the directory where data files will be stored
     * @param entityName the name of the entity
     * @param entityClass the class of the entity
     */
    public BaseFileRepository(String dataDirectory, String entityName, Class<T> entityClass) {
        this.dataDirectory = dataDirectory;
        this.entityName = entityName;
        this.entityClass = entityClass;

        // Configure ObjectMapper with JavaTimeModule for LocalDate support
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Create data directory if it doesn't exist
        createDataDirectory();

        // Initialize ID counter
        this.idCounter = new AtomicLong(getMaxId());
    }

    /**
     * Create the data directory if it doesn't exist.
     */
    private void createDataDirectory() {
        Path dirPath = Paths.get(dataDirectory);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create data directory: " + dataDirectory, e);
            }
        }
    }

    /**
     * Get the file path for the entity data.
     * 
     * @return the file path
     */
    protected String getFilePath() {
        return dataDirectory + File.separator + entityName + ".json";
    }

    /**
     * Get the next ID for a new entity.
     * 
     * @return the next ID
     */
    protected Long getNextId() {
        return idCounter.incrementAndGet();
    }

    /**
     * Get the maximum ID from existing entities.
     * 
     * @return the maximum ID
     */
    protected Long getMaxId() {
        List<T> entities = findAll();
        long maxId = 0;

        for (T entity : entities) {
            Long id = getId(entity);
            if (id != null && id > maxId) {
                maxId = id;
            }
        }

        return maxId;
    }

    /**
     * Save all entities to the file.
     * 
     * @param entities the list of entities to save
     */
    protected void saveAll(List<T> entities) {
        try {
            objectMapper.writeValue(new File(getFilePath()), entities);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save entities to file: " + getFilePath(), e);
        }
    }

    /**
     * Load all entities from the file.
     * 
     * @return the list of entities
     */
    protected List<T> loadAll() {
        File file = new File(getFilePath());
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(file, 
                    objectMapper.getTypeFactory().constructCollectionType(List.class, entityClass));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load entities from file: " + getFilePath(), e);
        }
    }

    /**
     * Find all entities.
     * 
     * @return the list of all entities
     */
    public List<T> findAll() {
        return loadAll();
    }

    /**
     * Find an entity by ID.
     * 
     * @param id the ID
     * @return an Optional containing the entity if found, or empty if not found
     */
    public Optional<T> findById(Long id) {
        return findAll().stream()
                .filter(entity -> getId(entity).equals(id))
                .findFirst();
    }

    /**
     * Save an entity.
     * 
     * @param entity the entity to save
     * @return the saved entity
     */
    public T save(T entity) {
        List<T> entities = findAll();
        Long id = getId(entity);

        if (id == null) {
            // New entity, assign ID
            setId(entity, getNextId());
            entities.add(entity);
        } else {
            // Existing entity, update
            boolean found = false;
            for (int i = 0; i < entities.size(); i++) {
                if (getId(entities.get(i)).equals(id)) {
                    entities.set(i, entity);
                    found = true;
                    break;
                }
            }

            if (!found) {
                entities.add(entity);
            }
        }

        saveAll(entities);
        return entity;
    }

    /**
     * Delete an entity by ID.
     * 
     * @param id the ID
     */
    public void deleteById(Long id) {
        List<T> entities = findAll();
        entities.removeIf(entity -> getId(entity).equals(id));
        saveAll(entities);
    }

    /**
     * Get the ID of an entity.
     * 
     * @param entity the entity
     * @return the ID
     */
    protected abstract Long getId(T entity);

    /**
     * Set the ID of an entity.
     * 
     * @param entity the entity
     * @param id the ID
     */
    protected abstract void setId(T entity, Long id);
}
