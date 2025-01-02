package net.avaxplay.itemfinder.api.v1;

import net.avaxplay.itemfinder.config.ImageStorageConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageUploadService {
    private final ImageStorageConfig imageStorageConfig;

    public ImageUploadService(ImageStorageConfig imageStorageConfig) {
        this.imageStorageConfig = imageStorageConfig;
    }

    /**
     * Uploads a file to the 'images-lost' directory.
     *
     * @param file The file to upload.
     * @return The accessible path to the uploaded file.
     * @throws IOException If an I/O error occurs.
     */
    public String uploadLostImage(MultipartFile file) throws IOException {
        return uploadFileToDirectory(file, imageStorageConfig.getLostImagesPath(), "images-lost");
    }

    /**
     * Uploads a file to the 'images-found' directory.
     *
     * @param file The file to upload.
     * @return The accessible path to the uploaded file.
     * @throws IOException If an I/O error occurs.
     */
    public String uploadFoundImage(MultipartFile file) throws IOException {
        return uploadFileToDirectory(file, imageStorageConfig.getFoundImagesPath(), "images-found");
    }

    /**
     * Uploads a file to the 'images-other' directory.
     *
     * @param file The file to upload.
     * @return The accessible path to the uploaded file.
     * @throws IOException If an I/O error occurs.
     */
    public String uploadOtherImage(MultipartFile file) throws IOException {
        return uploadFileToDirectory(file, imageStorageConfig.getOtherImagesPath(), "images-other");
    }

    /**
     * Generalized method to upload a file to a specified directory.
     *
     * @param file           The file to upload.
     * @param targetDirectory The target directory path.
     * @param subPath         The subdirectory name used for constructing the access URL.
     * @return The accessible path to the uploaded file.
     * @throws IOException If an I/O error occurs.
     */
    private String uploadFileToDirectory(MultipartFile file, Path targetDirectory, String subPath) throws IOException {
        // Generate a unique file name
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // Resolve the target path
        Path targetLocation = targetDirectory.resolve(uniqueFileName);

        // Copy the file to the target location
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Return the relative path for access
        return "/" + subPath + "/" + uniqueFileName;
    }

    /**
     * Extracts the file extension from the original file name.
     *
     * @param originalFilename The original file name.
     * @return The file extension, including the dot (e.g., ".jpg"), or an empty string if none.
     */
    private String getFileExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            return ""; // No extension
        }
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
