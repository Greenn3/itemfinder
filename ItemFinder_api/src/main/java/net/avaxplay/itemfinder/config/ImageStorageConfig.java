package net.avaxplay.itemfinder.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageStorageConfig {
    private static final Logger log = LoggerFactory.getLogger(ImageStorageConfig.class);

    @Value("${image.storage.path}")
    private String imageStoragePath;

//    @Value("${image.max-file-size-mb}")
//    private String imageFileSizeStr;
//    private long imageFileSizeBytes;

    private Path lostImagesPath;
    private Path foundImagesPath;
    private Path otherImagesPath;

    @PostConstruct
    public void init() throws IOException {
//        if (imageFileSizeStr == null) {
//            imageFileSizeBytes = 20 * 1024 * 1024;
//            log.warn("Environment Variable 'IMAGE_MAX-FILE-SIZE-MB' not set, setting to default 20MB ({}B)", imageFileSizeBytes);
//        }
//        try {
//            imageFileSizeBytes = Long.parseLong(imageFileSizeStr) * 1024 * 1024;
//        } catch (Exception e) {
//            imageFileSizeBytes = 20 * 1024 * 1024;
//            log.error("Failed to read 'IMAGE_MAX-FILE-SIZE-MB', setting to default 20MB ({}B)", imageFileSizeBytes);
//        }

        if (imageStoragePath == null || imageStoragePath.isBlank()) {
            throw new IllegalStateException("The required property 'image.storage.path' is not set or is empty!");
        }

        Path basePath = Paths.get(imageStoragePath);
        lostImagesPath = basePath.resolve("images-lost");
        foundImagesPath = basePath.resolve("images-found");
        otherImagesPath = basePath.resolve("images-other");

        // Create the directories if they don't exist
        Files.createDirectories(lostImagesPath);
        Files.createDirectories(foundImagesPath);
        Files.createDirectories(otherImagesPath);
    }

//    public long getImageFileSizeBytes() {
//        return imageFileSizeBytes;
//    }

    public Path getLostImagesPath() {
        return lostImagesPath;
    }

    public Path getFoundImagesPath() {
        return foundImagesPath;
    }

    public Path getOtherImagesPath() {
        return otherImagesPath;
    }
}
